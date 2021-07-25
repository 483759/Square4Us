package com.ssafy.square4us.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("MemberJoinPostRequest")
public class MemberJoinPostReq {
	@ApiModelProperty(name="email", example="ssafy@naver.com")
	String email;
	@ApiModelProperty(name="password", example="password1234")
	String password;
	@ApiModelProperty(name="nickname", example="윤이진")
	String nickname;
}
