package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.mvc.service.StudyService;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.ResponseFactory;
import com.ssafy.square4us.common.auth.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/study")
public class StudyController {
    private final StudyService studyService;
    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "스터디 생성", description = "스터디를 생성한다", responses = {
            @ApiResponse(responseCode = "201", description = "스터디 생성 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "스터디 생성 실패")})
    public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
                                                              @RequestBody @Parameter(name = "스터디 생성 정보", required = true) Study.CreatePostReq studyInfo) {

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        Study newStudy = studyService.createStudy(studyInfo, member);

        if (newStudy == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(Study.InfoGetRes.of(200, "스터디 생성 완료", newStudy.getId(), newStudy.getCategory(), newStudy.getName(), newStudy.getDismantleFlag(), newStudy.getDismantleDate()));
        //return ResponseEntity.status(HttpStatus.CREATED).body(StudyCreatePostRes.of(201, "스터디 생성", newStudy));
    }

    @GetMapping("")
    @Operation(summary = "스터디 목록 조회", description = "현재 모든 스터디의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> readAll() {
        List<Study> list = studyService.findAllStudies();
        if (list == null) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(Study.ListGetRes.of(200, "조회 성공", list));
        //return ResponseEntity.status(HttpStatus.CREATED).body(StudyListGetRes.of(200, "성공", list));
    }

    @GetMapping("{studyId}")
    @Operation(summary = "스터디 정보 조회", description = "특정 스터디의 정보를 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getStudyById(@PathVariable("studyId") Long studyId) {
        Study study = studyService.findByStudyId(studyId);

        if (study == null) {
            return ResponseFactory.noContent();
        }

        return ResponseEntity.ok(Study.InfoGetRes.of(200, "조회 성공", study.getId(), study.getCategory(), study.getName(), study.getDismantleFlag(), study.getDismantleDate()));
        //return ResponseEntity.status(HttpStatus.CREATED).body(Study.InfoGetRes.of(200, "성공", study));
    }

    @PostMapping("/{studyId}/resign")
    @Operation(summary = "스터디 탈퇴", description = "스터디를 탈퇴한다(비 리더)", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> resignStudy(@Parameter(hidden = true) Authentication authentication, @PathVariable("studyId") Long studyId) {
        if (authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }
        String email = memberDetails.getUsername();

        boolean flag = studyService.resign(email, studyId);
        if (!flag) return ResponseFactory.conflict();

        return ResponseFactory.ok();
    }

    @DeleteMapping("/{studyId}")
    @Operation(summary = "스터디 폐쇄", description = "특정 스터디를 없앤다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "409", description = "스터디 폐쇄 실패")
    })
    public ResponseEntity<? extends BasicResponseBody> deleteStudy(
            @Parameter(hidden = true) Authentication authentication, @PathVariable("studyId") Long studyId) {
        if (authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }
        String email = memberDetails.getUsername();
        Study study = studyService.findByStudyId(studyId);

        if (study == null) {
            return ResponseFactory.notFound();
        }

        boolean flag = studyService.deleteByStudyId(email, studyId);

        if (!flag) return ResponseFactory.conflict();

        return ResponseFactory.ok();
    }
}
