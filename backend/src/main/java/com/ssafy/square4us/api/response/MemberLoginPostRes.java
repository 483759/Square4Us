package com.ssafy.square4us.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@ApiModel("MemberLoginPostResponse")
public class MemberLoginPostRes extends BasicResponseBody{
	@ApiModelProperty(name="JWT Authentication Token")
	String accessToken;
	
	public static MemberLoginPostRes of(Integer statusCode, String message, String accessToken) {
		MemberLoginPostRes res = new MemberLoginPostRes(accessToken);
		res.setStatusCode(statusCode);
		res.setMessage(message);
		return res;
	}
}
