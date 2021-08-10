package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;
import com.ssafy.square4us.api.mvc.model.dto.ResponseFactory;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MeetingService;
import com.ssafy.square4us.api.mvc.service.MemberService;
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
@RequestMapping(value = "/api/study/{studyId}/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final MemberService memberService;

    public Member authentication(Authentication auth){
        if(auth==null) return null;
        MemberDetails memberDetails = (MemberDetails) auth.getDetails();
        String memberId = memberDetails.getUsername();

        return memberService.getMemberByEmail(memberId);
    }

    @PostMapping("")
    @Operation(summary = "미팅 생성", description = "미팅 생성한다", responses = {
            @ApiResponse(responseCode = "201", description = "미팅 생성 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "미팅 생성 실패")})
    public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
                                                              @PathVariable("studyId") Long studyId,
                                                              @RequestBody @Parameter(name = "미팅 생성 정보", required = true) MeetingDTO.GeneratePostReq meetingInfo) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        MeetingDTO newMeeting = meetingService.createMeeting(studyId, meetingInfo);

        if (newMeeting == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(MeetingDTO.CreatePostRes.of(201, "미팅 생성", newMeeting.getId()));
    }

    @GetMapping("{meetingId}")
    @Operation(summary = "미팅 정보", description = "meetingId에 해당하는 미팅 정보를 반환한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "미팅이 존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> enterMeeting(@Parameter(hidden = true) Authentication authentication,
                                                                    @PathVariable("studyId") Long studyId,
                                                                    @PathVariable("meetingId") Long meetId) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        Member member = authentication(authentication);
        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        MeetingDTO meetingInfo = meetingService.enterMeeting(meetId);

        if (meetingInfo == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(MeetingDTO.EnterGetRes.of(200, "성공", meetingInfo));
    }

    @GetMapping("")
    @Operation(summary = "미팅 목록 조회", description = "현재 모든 미팅의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> readAll() {
        List<MeetingDTO> list = meetingService.findAllMeetings();
        if (list == null) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(MeetingDTO.ListGetRes.of(200, "조회 성공", list));
    }

}
