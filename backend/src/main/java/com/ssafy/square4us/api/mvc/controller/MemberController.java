package com.ssafy.square4us.api.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MemberJoinPostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.MemberInfoGetRes;
import com.ssafy.square4us.common.auth.MemberDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

//@Tag(description = "멤버 API", name = "Member")
@RestController
@RequestMapping(value = "/api/v1/member")
public class MemberController {
	@Autowired MemberService memberService;
	
	@PostMapping("/join")
	@Operation(summary = "회원 가입", description = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.", responses = {
        @ApiResponse(responseCode = "201", description = "성공"),
        @ApiResponse(responseCode = "409", description = "중복된 계정 오류"),
        @ApiResponse(responseCode = "503", description = "회원가입 실패"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
	public ResponseEntity<? extends BasicResponseBody> register(
			@RequestBody @Parameter(name="회원가입 정보", required = true) MemberJoinPostReq joinInfo) {
		
		Member confirmMember = memberService.getMemberByEmail(joinInfo.getEmail());
		if(confirmMember!=null) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BasicResponseBody.of(409, "이미 존재하는 계정입니다"));
		}
		
		//임의로 리턴된 Member 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Member member = memberService.createMember(joinInfo);
		
		if(member==null)
		{
			return ResponseEntity.status(503).body(BasicResponseBody.of(503, "회원가입에 실패했습니다."));
		}
		return ResponseEntity.status(201).body(BasicResponseBody.of(201, "회원가입 성공"));
	}
	
	@GetMapping("/me")
	@Operation(summary = "회원 본인 정보 조회", description = "로그인한 회원 본인의 정보를 응답한다.", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "401", description = "인증 실패"),
        @ApiResponse(responseCode = "404", description = "사용자 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
	public ResponseEntity<MemberInfoGetRes> getUserInfo(@Parameter(hidden = true) Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		
		MemberDetails userDetails = (MemberDetails)authentication.getDetails();
		
		String userId = userDetails.getUsername();
		Member user = memberService.getMemberByEmail(userId);
		
		return ResponseEntity.status(200).body(MemberInfoGetRes.of(user));
	}
}
