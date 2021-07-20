package com.ssafy.square4us.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BasicResponseBody")
public class BasicResponseBody {
	@ApiModelProperty(name = "Response Code", example = "200")
	Integer statusCode = null;

	@ApiModelProperty(name = "Response Message", example = "정상")
	String message = null;
	
	public BasicResponseBody(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public static BasicResponseBody of(Integer statusCode, String message) {
		return new BasicResponseBody(statusCode, message);
	}
}
