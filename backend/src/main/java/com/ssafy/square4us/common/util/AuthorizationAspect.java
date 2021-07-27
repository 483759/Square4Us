package com.ssafy.square4us.common.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.common.auth.MemberDetails;
import com.ssafy.square4us.common.handler.AuthorizationHeaderNotExistsException;

import lombok.Setter;

//@ConfigurationProperties(prefix = "demo.token")
@Aspect
@Component
public class AuthorizationAspect {

	@Setter
	private String apiKey;
	@Setter
	private String secretKey;
	@Autowired
	private MemberService memberService;

	//@Before("execution(public * com.ssafy.square4us.api.mvc.controller..*Controller.*(..)) ")
	public void insertAdminLog(JoinPoint joinPoint)
			throws UnsupportedEncodingException, TokenExpiredException, JWTVerificationException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String header = request.getHeader(JwtTokenProvider.HEADER_STRING);

		if (header == null || !header.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
			throw new AuthorizationHeaderNotExistsException();
		}

		// try {
		JWTVerifier verifier = JwtTokenProvider.getVerifier();
		DecodedJWT decodedJWT = verifier.verify(header.replace(JwtTokenProvider.TOKEN_PREFIX, ""));
		String email = decodedJWT.getSubject();

		// if(email==null) throw new Exception();
		Member member = memberService.getMemberByEmail(email);
		// if(member==null) throw new Exception();
		MemberDetails memberDetails = new MemberDetails(member);

		UsernamePasswordAuthenticationToken jwtAuthToken = new UsernamePasswordAuthenticationToken(email,
				decodedJWT.getClaim("USER_ROLE"), memberDetails.getAuthorities());
		jwtAuthToken.setDetails(memberDetails);

		SecurityContextHolder.getContext().setAuthentication((Authentication) jwtAuthToken);
//		}catch(Exception e) {
//			e.printStackTrace();
//			ResponseBodyWriteUtil.sendError(request, response, e);
//			return;
//		}
//		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));
//		
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();		
//		String authorization = request.getHeader("Authorization");
//		if(StringUtils.isBlank(authorization)){ 
//			throw new AuthorizationHeaderNotExistsException();
//		}
//		if(Pattern.matches("^Bearer .*", authorization)) {
//			authorization = authorization.replaceAll("^Bearer( )*", "");
//			Jws<Claims> jwsClaims = Jwts.parserBuilder()
//					.setSigningKey(key)
//					.build()
//					.parseClaimsJws(authorization);
//			
//			if(jwsClaims.getBody() != null) {
//				Claims claims = jwsClaims.getBody();
//				if(!claims.containsKey("apiKey") || !apiKey.equals(claims.get("apiKey").toString())
//						|| claims.getExpiration() == null) {
//					throw new InvalidTokenException();
//				} 
//				long exp = claims.getExpiration().getTime();
//				if(exp < new Date().getTime()) {
//					throw new TokenExpiredException();
//				}
//			}
//		} else {
//			throw new InvalidTokenException();
//		}
	}

//	@Before("execution(public * com.ssafy.square4us.api.mvc.controller..*Controller.*(..)) ")
//	public void insertAdminLog(JoinPoint joinPoint) throws AuthorizationHeaderNotExistsException, InvalidTokenException {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();		
//
//		String authorization = request.getHeader("Authorization");
//		if(StringUtils.isBlank(authorization)){
//			throw new AuthorizationHeaderNotExistsException();
//		}
//		authorization = authorization.replaceAll("^Basic( )*", "");
//		try {
//			String decodedStr = new String(Base64.getDecoder().decode(authorization));
//			if(decodedStr.indexOf(":") < 0)
//				throw new InvalidTokenException();
//		} catch(Exception e) {
//			throw new InvalidTokenException();
//		}
//	}	
}