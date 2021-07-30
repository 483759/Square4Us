package com.ssafy.square4us.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 회원가입 API 요청에 필요한 Request Body
 * [POST] /member/join
 * */
@Getter
@Setter
@ToString
@Schema(description = "MemberJoinPostRequest")
public class MemberJoinPostReq {
	@Schema(name="email", example="ssafy@naver.com")
	String email;
	@Schema(name="password", example="password1234")
	String password;
	@Schema(name="nickname", example="윤이진")
	String nickname;
}
