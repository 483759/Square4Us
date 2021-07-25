package com.ssafy.square4us.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.square4us.common.auth.MemberDetails;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	private static String secretKey;
	private static int expiryTime;

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String ISSUER = "483759@naver.com";

	@Autowired
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiryTime}") int expiryTime) {
		this.secretKey = secretKey;
		this.expiryTime = expiryTime;
	}

	public static void setExpiryTime(int expiryTime) {
		JwtTokenProvider.expiryTime = expiryTime;
	}
	
	public static JWTVerifier getVerifier() {
		return JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
				.withIssuer(ISSUER)
				.build();
	}
	
	public static String generateToken(MemberDetails memberDetails) {
		String authToken = null;

//		String role = memberDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet())
//				.iterator().next();

		try {
			authToken = JWT.create()
					.withSubject(memberDetails.getUsername())
					.withExpiresAt(new Date(new Date().getTime() + expiryTime))
					.withIssuer(ISSUER)
	                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
					//.withClaim("USER_ROLE", role)
	                .sign(Algorithm.HMAC512(secretKey.getBytes()));
			return authToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String generateToken(Instant expires, MemberDetails memberDetails) {
		String authToken = null;

		String role = memberDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet())
				.iterator().next();

		try {
			authToken = JWT.create()
					.withSubject(memberDetails.getUsername())
					.withExpiresAt(Date.from(expires))
					.withIssuer(ISSUER)
	                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
					//.withClaim("USER_ROLE", role)
	                .sign(Algorithm.HMAC512(secretKey.getBytes()));
			return authToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
