package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
import com.ssafy.square4us.api.mvc.model.dto.ResponseFactory;
import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.mvc.service.StudyService;
import com.ssafy.square4us.common.auth.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Basic;
import java.util.List;

@RestController
@RequestMapping(value = "/api/study")
public class StudyController {
    private final StudyService studyService;
    private final MemberService memberService;

    public StudyController(StudyService studyService, MemberService memberService) {
        this.studyService = studyService;
        this.memberService = memberService;
    }

    @PostMapping("")
    @Operation(summary = "스터디 생성", description = "스터디를 생성한다", responses = {
            @ApiResponse(responseCode = "201", description = "스터디 생성 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "스터디 생성 실패")})
    public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
                                                              @RequestBody @Parameter(name = "스터디 생성 정보", required = true) StudyDTO.CreatePostReq studyInfo) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        StudyDTO newStudy = studyService.createStudy(studyInfo, member);

        if (newStudy == null) {
            return ResponseFactory.forbidden();
        }

        Long leaderId = studyService.findStudyLeader(newStudy.getId());

        return ResponseEntity.ok(StudyDTO.InfoGetRes.of(200, "스터디 생성 완료", newStudy.getId(), newStudy.getCategory(), newStudy.getName(), leaderId, null));
    }

    @PostMapping("{studyId}")
    @Operation(summary = "스터디 가입 신청")
    public ResponseEntity<? extends BasicResponseBody> joinStudy(@Parameter(hidden = true) Authentication authentication, @PathVariable Long studyId) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        boolean result = studyService.joinStudy(studyId, member);

        if (result) {
            return ResponseFactory.ok();
        }
        return ResponseFactory.forbidden();
    }

    @PostMapping("{studyId}/accept/{memberId}")
    @Operation(summary = "스터디 가입 승인")
    public ResponseEntity<? extends BasicResponseBody> acceptJoinRequest(@Parameter(hidden = true) Authentication authentication, @PathVariable Long studyId, @PathVariable Long memberId) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String email = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(email);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        boolean result = studyService.acceptStudyJoin(studyId, memberId, member);

        if (result) {
            return ResponseFactory.ok();
        }
        return ResponseFactory.forbidden();
    }

    @PostMapping("{studyId}/reject/{memberId}")
    @Operation(summary = "스터디 가입 거절")
    public ResponseEntity<? extends BasicResponseBody> rejectJoinRequest(@Parameter(hidden = true) Authentication authentication, @PathVariable Long studyId, @PathVariable Long memberId) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String email = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(email);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        boolean result = studyService.rejectStudyJoin(studyId, memberId, member);

        if (result) {
            return ResponseFactory.ok();
        }
        return ResponseFactory.forbidden();
    }

    @PatchMapping("{studyId}/delegate/{memberId}")
    @Operation(summary = "스터디 리더 권한 위임")
    public ResponseEntity<? extends BasicResponseBody> delegateLeader(@Parameter(hidden = true) Authentication authentication, @PathVariable Long studyId, @PathVariable Long memberId) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String email = memberDetails.getUsername();

        Member originLeader = memberService.getMemberByEmail(email);

        if (originLeader == null) {
            return ResponseFactory.unauthorized();
        }

        boolean result = studyService.delegateLeader(studyId, originLeader.getId(), memberId);

        if (result) {
            return ResponseFactory.ok();
        }
        return ResponseFactory.conflict();
    }


    @GetMapping("/me/list")
    @Operation(summary = "내 스터디 조회", description = "내가 가입한 스터디의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getStudyList(@Parameter(hidden = true) Authentication authentication) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String email = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(email);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        List<StudyDTO> list = studyService.findStudiesByMember(member.getId());

        return ResponseEntity.ok(StudyDTO.ListGetRes.of(200, "조회 성공", list));
    }

    @GetMapping("")
    @Operation(summary = "스터디 목록 조회", description = "현재 모든 스터디의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> readAllWithPaging(@Parameter int page, @Parameter int size, @Parameter(required = false) Sort sort) {
        Pageable pageable;

        if (sort == null) {
            pageable = PageRequest.of(page, size);
        } else {
            pageable = PageRequest.of(page, size, sort);
        }

        Page<StudyDTO> list = studyService.findStudiesWithPaging(pageable);
        if (list == null) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(StudyDTO.PageableListGetRes.of(200, "조회 성공", list));
    }

    @GetMapping("search")
    @Operation(summary = "스터디 목록 검색 및 조회", description = "스터디의 검색 결과를 반환한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getStudyListWitSearchingAndPaging(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                                        String key,
                                                                                        String word) {
        if(!key.equals("category") && !key.equals("name")) {
            return ResponseFactory.forbidden();
        }
        Page<StudyDTO> studyList = studyService.getStudyListWithSearchingAndPaging(pageable, key, word);
        if(studyList == null || studyList.getSize() == 0) {
            return ResponseFactory.noContent();
        }

        return ResponseEntity.ok(StudyDTO.PageableListGetRes.of(200, "조회 성공", studyList));
    }


    @GetMapping("{studyId}")
    @Operation(summary = "스터디 정보 조회", description = "특정 스터디의 정보를 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getStudyById(@PathVariable("studyId") Long studyId) {
        StudyDTO study = studyService.findByStudyId(studyId);

        if (study == null) {
            return ResponseFactory.noContent();
        }

        Long leaderId = studyService.findStudyLeader(studyId);

        return ResponseEntity.ok(StudyDTO.InfoGetRes.of(200, "조회 성공", study.getId(), study.getCategory(), study.getName(), leaderId, null));
    }

    @PostMapping("/{studyId}/withdraw")
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
        Member member = memberService.getMemberByEmail(email);

        boolean flag = studyService.withdrawStudy(member.getId(), studyId);
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
        StudyDTO study = studyService.findByStudyId(studyId);

        if (study == null) {
            return ResponseFactory.notFound();
        }

        boolean flag = studyService.deleteByStudyId(email, studyId);

        if (!flag) return ResponseFactory.conflict();

        return ResponseFactory.ok();
    }

    @PostMapping("{studyId}/profile")
    @Operation(summary = "스터디 프로필 사진 입력")
    public ResponseEntity<? extends BasicResponseBody> updateProfile(@Parameter(hidden = true) Authentication authentication,
                                                                     @PathVariable("studyId") Long studyId,
                                                                     MultipartFile profile) {
        if(authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if(memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        StudyDTO study = studyService.findByStudyId(studyId);

        if(study == null) {
            return ResponseFactory.notFound();
        }

        Boolean flag = studyService.isLeaderOfThisStudy(studyId, memberService.getMemberByEmail(email).getId());
        if(!flag.booleanValue()) {
            return ResponseFactory.forbidden();
        }

        try {
            studyService.updateProfile(studyId, profile);
        } catch(Exception e) {
            return ResponseFactory.internalServerError();
        }
        return ResponseFactory.ok();
    }

    @PutMapping("{studyId}/profile")
    @Operation(summary = "스터디 프로필 사진 삭제")
    public ResponseEntity<? extends BasicResponseBody> deleteProfile(@Parameter(hidden = true) Authentication authentication,
                                                                     @PathVariable("studyId") Long studyId) {
        if(authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if(memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        StudyDTO study = studyService.findByStudyId(studyId);

        if(study == null) {
            return ResponseFactory.notFound();
        }

        Boolean flag = studyService.isLeaderOfThisStudy(studyId, memberService.getMemberByEmail(email).getId());
        if(!flag.booleanValue()) {
            return ResponseFactory.forbidden();
        }

        try {
            studyService.deleteProfile(studyId);
        } catch(Exception e) {
            return ResponseFactory.internalServerError();
        }

        return ResponseFactory.ok();
    }

    @GetMapping("isLeader/{studyId}")
    public ResponseEntity<? extends BasicResponseBody> isLeaderOfThisStudy(@Parameter(hidden = true) Authentication authentication,
                                                                       @PathVariable("studyId") Long studyId) {
        if(authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if(memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        StudyDTO study = studyService.findByStudyId(studyId);

        if(study == null) {
            return ResponseFactory.notFound();
        }
        Boolean flag = studyService.isLeaderOfThisStudy(studyId, memberService.getMemberByEmail(email).getId());

        return ResponseEntity.ok(StudyDTO.LeaderFlagGetRes.of(200, "성공", flag));
    }
}
