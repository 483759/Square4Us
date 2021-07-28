package com.ssafy.square4us;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.square4us.api.mvc.controller.MemberController;
import com.ssafy.square4us.api.mvc.model.entity.BaseEntity;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MemberJoinPostReq;
import com.ssafy.square4us.api.request.MemberLoginPostReq;
import com.ssafy.square4us.api.response.BasicResponseBody;
import com.ssafy.square4us.common.auth.MemberDetailsService;
import com.ssafy.square4us.common.util.JwtTokenProvider;
import com.ssafy.square4us.common.util.ResponseBodyWriteUtil;

/**
 * Member, Authorization 관련된 컨트롤러는 WebMvcTest 적합하지 않은듯(등록할 빈이 너무 많음)
 * */
//@WebMvcTest(MemberController.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;

	@Test
	public void validLogin() {
		MemberLoginPostReq loginInfo = new MemberLoginPostReq();
		loginInfo.setEmail("ssafy@naver.com");
		loginInfo.setPassword("password1234");

		/*
		 * AuthController 실행시켜도 getMemberByEmail 메서드 실행 안 됨
		 * -> WebMvcTest 때문이었음()
		 */
		try {
			ResultActions result = 
					mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
					.accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(loginInfo))
					.contentType(MediaType.APPLICATION_JSON));

			result.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				//.andExpect(MockMvcResultMatchers.model().attribute(name, matcher))
				;
		} catch (Exception e) {
			assertThat(e);
		}
	}
	
	@Test
	public void invalidPassword() {
		MemberLoginPostReq loginInfo = new MemberLoginPostReq();
		loginInfo.setEmail("ssafy@naver.com");
		loginInfo.setPassword("wrongpassword1234");
		//잘못된 비밀번호 테스트

		try {
			ResultActions result = 
					mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
					.accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(loginInfo))
					.contentType(MediaType.APPLICATION_JSON));

			result.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				;
		} catch (Exception e) {
			assertThat(e);
		}
	}

	@Test
	public void nonExistentAccount() {
		MemberLoginPostReq loginInfo = new MemberLoginPostReq();
		loginInfo.setEmail("no-exAccount@naver.com");
		loginInfo.setPassword("password1234");
		//존재하지 않는 계정 테스트

		try {
			ResultActions result = 
					mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
					.accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(loginInfo))
					.contentType(MediaType.APPLICATION_JSON));

			result.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				;
		} catch (Exception e) {
			assertThat(e);
		}
	}
	
	@Test
	@Rollback
	public void validJoin() {
		MemberJoinPostReq joinInfo = new MemberJoinPostReq();
		joinInfo.setEmail("jointest@domain.com");
		joinInfo.setPassword("test1234");
		joinInfo.setNickname("회원가입테스트");
		
		try {
			ResultActions result = 
					mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
							.accept(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(joinInfo))
							.contentType(MediaType.APPLICATION_JSON)
							);
			
			result.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isCreated())
			;
		}catch(Exception e) {
			assertThat(e);
		}
	}

	@Test
	@Rollback
	public void duplicateAccount() {
		MemberJoinPostReq joinInfo = new MemberJoinPostReq();
		joinInfo.setEmail("ssafy@naver.com");
		joinInfo.setPassword("test1234");
		joinInfo.setNickname("중복회원가입테스트");
		
		try {
			ResultActions result = 
					mockMvc.perform(MockMvcRequestBuilders.post("/member/join")
							.accept(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(joinInfo))
							.contentType(MediaType.APPLICATION_JSON)
							);
			
			result.andDo(print())
			.andExpect(MockMvcResultMatchers.status().isNotAcceptable())
			;
		}catch(Exception e) {
			assertThat(e);
		}
	}
}
