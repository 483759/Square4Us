package com.ssafy.square4us.common.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ssafy.square4us.common.util.JwtTokenProvider;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired JwtTokenProvider jwtTokenUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	} 
}
