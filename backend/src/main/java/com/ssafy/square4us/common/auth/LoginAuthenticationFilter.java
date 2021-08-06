package com.ssafy.square4us.common.auth;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.common.util.JwtTokenProvider;
import com.ssafy.square4us.common.util.ResponseBodyWriteUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFilter extends BasicAuthenticationFilter {

    private MemberService memberService;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager, MemberService memberService) {
        super(authenticationManager);
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(JwtTokenProvider.HEADER_STRING);

        if (header == null || !header.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            JWTVerifier verifier = JwtTokenProvider.getVerifier();
            DecodedJWT decodedJWT = verifier.verify(header.replace(JwtTokenProvider.TOKEN_PREFIX, ""));
            String email = decodedJWT.getSubject();

            //if(email==null) throw new Exception();
            Member member = memberService.getMemberByEmail(email);
            //if(member==null) throw new Exception();
            MemberDetails memberDetails = new MemberDetails(member);

            UsernamePasswordAuthenticationToken jwtAuthToken = new UsernamePasswordAuthenticationToken(email, decodedJWT.getClaim("USER_ROLE"), memberDetails.getAuthorities());
            jwtAuthToken.setDetails(memberDetails);

            SecurityContextHolder.getContext().setAuthentication((Authentication) jwtAuthToken);
        } catch (SecurityException ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        } catch (IllegalArgumentException ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        } catch (JWTDecodeException ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        } catch (JWTVerificationException ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        } catch (Exception e) {
            ResponseBodyWriteUtil.sendError(request, response, e);
            return;
        }

        chain.doFilter(request, response);
    }


}
