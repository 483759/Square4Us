package com.ssafy.square4us.api.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MeetingService;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MeetingCreatePostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.MeetingCreatePostRes;
import com.ssafy.square4us.api.response.MeetingListGetRes;
import com.ssafy.square4us.common.auth.MemberDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/meeting")
public class MeetingController {
    
    private final MeetingService meetingService;
    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "미팅 생성", description = "미팅 생성한다", responses = {
            @ApiResponse(responseCode = "201", description = "미팅 생성 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "미팅 생성 실패") })
    public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
                                                              @RequestBody @Parameter(name = "미팅 생성 정보", required = true) MeetingCreatePostReq meetingInfo) {

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);

        if(member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "생성 권한이 존재하지 않습니다"));
        }

        Meeting newMeeting = meetingService.createMeeting(meetingInfo);

        if(newMeeting==null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BasicResponseBody.of(403, "미팅 생성에 실패했습니다"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(MeetingCreatePostRes.of(201, "미팅 생성", newMeeting));
    }

    @GetMapping("")
    @Operation(summary = "미팅 목록 조회", description = "현재 모든 미팅의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음") })
    public ResponseEntity<? extends BasicResponseBody> readAll(){
        List<Meeting> list = meetingService.findAllMeetings();
        if(list==null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BasicResponseBody.of(204, "존재하지 않음"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(MeetingListGetRes.of(200, "성공", list));
    }

}
