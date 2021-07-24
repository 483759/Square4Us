package com.ssafy.square4us.api.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MemberLoginPostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.api.response.MemberLoginPostRes;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	MemberService memberService;

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "이메일과 패스워드를 입력하여 로그인한다")
	@ApiResponses({ @ApiResponse(code = 200, message = "로그인 성공", response = MemberLoginPostRes.class),
			@ApiResponse(code = 401, message = "일치하지 않는 비밀번호", response = BasicResponseBody.class),
			@ApiResponse(code = 404, message = "존재하지 않는 계정", response = BasicResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BasicResponseBody.class) })
	public ResponseEntity<? extends BasicResponseBody> login(
			 @ApiParam(value = "로그인 정보", required = true) @RequestBody MemberLoginPostReq loginInfo) {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();
		String accessToken="";

		try {
			Member member = memberService.getMemberByEmail(email);

			if (member == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BasicResponseBody.of(404, "존재하지 않는 계정"));
			}
			if (member.getPassword().equals(password) == false) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "일치하지 않는 비밀번호"));
			}
			return ResponseEntity.ok(MemberLoginPostRes.of(200, "로그인 성공", accessToken));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponseBody.of(500, "서버 오류"));
		}
	}

}
