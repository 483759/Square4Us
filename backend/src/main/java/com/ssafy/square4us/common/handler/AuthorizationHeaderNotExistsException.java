package com.ssafy.square4us.common.handler;

public class AuthorizationHeaderNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 4858506469476160448L;
	public AuthorizationHeaderNotExistsException() {
		super("Authorization 헤더가 존재하지 않습니다.");
	}
}