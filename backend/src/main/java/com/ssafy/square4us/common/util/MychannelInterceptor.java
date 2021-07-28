package com.ssafy.square4us.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket Command에 따라 서버에 로그를 기록하는 Interceptor
 * logger의 내용을 보기 위해서는 application.yml에 
 * logging.level.root를 설정해야함
 */
@Slf4j
public class MychannelInterceptor implements ChannelInterceptor {

	//private static final Logger log = LoggerFactory.getLogger(MychannelInterceptor.class);

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		StompCommand command = accessor.getCommand();

		if (command.compareTo(StompCommand.SUBSCRIBE) == 0) {
			String destination = accessor.getDestination();
			log.info("구독 주소: ", destination);
		} else if (command.compareTo(StompCommand.CONNECT) == 0) {
			log.info("사용자 연결");
		} else if (command.compareTo(StompCommand.DISCONNECT) == 0) {
			log.info("사용자 연결 해제");
		}
		return message;
	}
}
