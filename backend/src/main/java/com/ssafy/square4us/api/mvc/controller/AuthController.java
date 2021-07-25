package com.ssafy.square4us.api.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MemberLoginPostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.MemberLoginPostRes;
import com.ssafy.square4us.common.auth.MemberDetails;
import com.ssafy.square4us.common.util.JwtTokenUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	MemberService memberService;
	@Autowired
	PasswordEncoder passwordEncoder;

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
			if (passwordEncoder.matches(password, member.getPassword()) == false) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "일치하지 않는 비밀번호"));
			}
			return ResponseEntity
					.ok(MemberLoginPostRes.of(200, "로그인 성공", JwtTokenUtil.generateToken(new MemberDetails(member))));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponseBody.of(500, "서버 오류"));
		}
	}

}
