package com.ssafy.square4us.api.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.mvc.service.StudyService;
import com.ssafy.square4us.api.request.StudyCreatePostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.StudyCreatePostRes;
import com.ssafy.square4us.common.auth.MemberDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/api/study")
public class StudyController {
	@Autowired
	StudyService studyService;
	@Autowired
	MemberService memberService;

	@PostMapping("")
	@Operation(summary = "스터디 생성", description = "스터디를 생성한다", responses = {
			@ApiResponse(responseCode = "201", description = "스터디 생성 성공"),
			@ApiResponse(responseCode = "401", description = "권한 없음"),
			@ApiResponse(responseCode = "403", description = "스터디 생성 실패") })
	public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
			@RequestBody @Parameter(name = "스터디 생성 정보", required = true) StudyCreatePostReq studyInfo) {

		MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

		String memberId = memberDetails.getUsername();
		Member member = memberService.getMemberByEmail(memberId);
		
		if(member == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "생성 권한이 존재하지 않습니다"));
		}

		Study newStudy = studyService.createStudy(studyInfo, member);
		
		if(newStudy==null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BasicResponseBody.of(403, "스터디 생성에 실패했습니다"));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(StudyCreatePostRes.of(201, "스터디 생성", newStudy));
	}
}
