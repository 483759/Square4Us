package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.mvc.service.StudyService;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.ResponseFactory;
import com.ssafy.square4us.api.response.StudyCreatePostRes;
import com.ssafy.square4us.api.response.StudyListGetRes;
import com.ssafy.square4us.common.auth.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            return ResponseFactory.Unauthorized();
        }

        Study newStudy = studyService.createStudy(studyInfo, member);

        if (newStudy == null) {
            return ResponseFactory.Forbidden();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(StudyCreatePostRes.of(201, "스터디 생성", newStudy));
    }

    @GetMapping("")
    @Operation(summary = "스터디 목록 조회", description = "현재 모든 스터디의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> readAll() {
        List<Study> list = studyService.findAllStudies();
        if (list == null) {
            return ResponseFactory.NoContent();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(StudyListGetRes.of(200, "성공", list));
    }

    @GetMapping("{studyId}")
    @Operation(summary = "스터디 정보 조회", description = "특정 스터디의 정보를 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getStudyById(@PathVariable("studyId") Long studyId) {
        Study study = studyService.findByStudyId(studyId);

        if (study == null) {
            return ResponseFactory.NoContent();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Study.InfoGetRes.of(200, "성공", study));
    }

    @DeleteMapping("/{studyId}")
    @Operation(summary = "스터디 폐쇄", description = "특정 스터디를 없앤다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<? extends BasicResponseBody> deleteStudy(@PathVariable("studyId") Long studyId) {
        Study study = studyService.findByStudyId(studyId);

        if (study == null) {
            return ResponseFactory.NotFound();
        }

        boolean flag = studyService.deleteByStudyId(studyId);

        if (!flag) return ResponseFactory.Conflict();

        return ResponseFactory.Ok();
    }
}
