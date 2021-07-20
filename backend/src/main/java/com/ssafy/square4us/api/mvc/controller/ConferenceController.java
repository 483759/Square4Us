package com.ssafy.square4us.api.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "회의실 API", tags = "Conference")
@RestController
@RequestMapping(value = "/conf")
public class ConferenceController {

}
