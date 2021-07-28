package com.ssafy.square4us.api.mvc.dto;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;

/**
 * 채팅 메시지 Object를 정의하는 Message 클래스
 * */
@Getter
@ToString
public class Message {
	private Long messageId;
	private String content;
	private Long confId;
	private String senderNickname;
	private MessageType type;
	private Date sendAt;
}
