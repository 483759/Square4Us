package com.ssafy.square4us.api.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "웹소켓 API", tags = "websocket")
@RestController
@RequestMapping(value = "/ws")
public class WebSocketController {

}
