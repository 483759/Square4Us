package com.ssafy.square4us.api.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MemberJoinPostReq;
import com.ssafy.square4us.api.request.MemberLoginPostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.MemberInfoGetRes;
import com.ssafy.square4us.api.response.MemberLoginPostRes;
import com.ssafy.square4us.common.auth.MemberDetails;
import com.ssafy.square4us.common.util.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

//@Tag(description = "멤버 API", name = "Member")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "이메일과 패스워드를 입력하여 로그인한다", responses = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "401", description = "일치하지 않는 비밀번호"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 계정"),
			@ApiResponse(responseCode = "500", description = "서버 오류"), })
	public ResponseEntity<? extends BasicResponseBody> login(
			@Parameter(name = "로그인 정보", required = true) @RequestBody MemberLoginPostReq loginInfo) {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();

		try {
			Member member = memberService.getMemberByEmail(email);

			if (member == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BasicResponseBody.of(404, "존재하지 않는 계정"));
			}
			if (new BCryptPasswordEncoder().matches(password, member.getPassword()) == false) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "일치하지 않는 비밀번호"));
			}
			return ResponseEntity
					.ok(MemberLoginPostRes.of(200, "로그인 성공", JwtTokenProvider.generateToken(new MemberDetails(member))));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponseBody.of(500, "서버 오류"));
		}
	}
	
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
			return ResponseEntity.status(HttpStatus.CONFLICT).body(BasicResponseBody.of(409, "이미 존재하는 계정입니다"));
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
			@ApiResponse(responseCode = "500", description = "서버 오류") })
	public ResponseEntity<MemberInfoGetRes> getUserInfo(@Parameter(hidden = true) Authentication authentication) {
		MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

		String email = memberDetails.getUsername();
		Member member = memberService.getMemberByEmail(email);

		return ResponseEntity.status(200).body(MemberInfoGetRes.of(member));
	}
}
