package com.ssafy.square4us.api.mvc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.square4us.api.mvc.dto.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
	//private final MessageService messageService;
	private final SimpMessagingTemplate template;

	@MessageMapping("/msg")
	public void sendMessage(@Payload Message chatMessage) {
		log.info("전달 메시지: " + chatMessage);
		
//		messageService.createMessage(chatMessage);	채팅 메시지를 저장하는 로직이 들어가면 됩니다
		template.convertAndSend("/sub/"+chatMessage.getConfId(), chatMessage);
	}

}
