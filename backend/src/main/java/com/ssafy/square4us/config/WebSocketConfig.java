package com.ssafy.square4us.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.ssafy.square4us.common.util.MychannelInterceptor;

/**
 * WebSocket에 대한 설정을 정의하는 Configuration Class
 * */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//EndPoint가 /ws인 요청에 대해 STOMP 프로토콜 적용
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();		
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub");		//publisher: message-handling methods로 라우팅
		registry.enableSimpleBroker("/sub");		//subscriber: topic으로 시작되는 메시지가 message broker로 라우팅
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		//모든 WebSocket 작업에 대해 Interceptor를 등록한다
		registration.interceptors(new MychannelInterceptor());
	}
}
