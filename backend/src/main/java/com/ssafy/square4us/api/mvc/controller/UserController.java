package com.ssafy.square4us.api.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "유저 API", tags = "user")
@RestController
@RequestMapping(value = "/user")
public class UserController {

}
