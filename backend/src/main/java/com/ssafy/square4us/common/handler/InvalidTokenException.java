package com.ssafy.square4us.common.handler;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = -2832108568693227235L;
	public InvalidTokenException() {
		super("유효하지 않은 토큰입니다.");
	}
}