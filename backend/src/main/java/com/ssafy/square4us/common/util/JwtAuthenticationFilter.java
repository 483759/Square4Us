package com.ssafy.square4us.common.util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ssafy.square4us.api.mvc.service.MemberService;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter{
	private MemberService memberService;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, MemberService memberService) {
		super(authenticationManager);
		this.memberService = memberService;
	}
	
	
}
