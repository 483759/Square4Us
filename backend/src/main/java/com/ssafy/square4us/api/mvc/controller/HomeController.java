package com.ssafy.square4us.api.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.response.BasicResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "기본 API", tags = "basic")
@RestController
@RequestMapping(value = "/square")
//CrossOrigin Annotation 추가 예정
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("")
	@ApiOperation(value = "메인 화면", notes = "메인 화면에 접속한다.")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Success", response = BasicResponseBody.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponseBody.class) 
		})
	public ResponseEntity<BasicResponseBody> hello() {
		return ResponseEntity.status(200).body(BasicResponseBody.of(200, "Success"));
	}

}
