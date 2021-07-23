package com.ssafy.square4us.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자의 로그인 API 요청에 필요한 Request Body
 * [POST] /member/login
 * */
@Getter
@Setter
@ToString
@ApiModel("MemberLoginPostRequest")
public class MemberLoginPostReq {
	@ApiModelProperty(name="email", example="ssafy@naver.com")
	String email;
	@ApiModelProperty(name="password", example="password1234")
	String password;
}
