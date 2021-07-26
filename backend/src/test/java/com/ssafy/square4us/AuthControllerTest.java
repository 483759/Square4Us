package com.ssafy.square4us;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.request.MemberLoginPostReq;
import com.ssafy.square4us.common.auth.MemberDetailsService;

/**
 * 로그인 MVC 테스트
 * */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(false)
class AuthControllerTest {

	@Autowired MockMvc mockMvc;

	@Autowired ObjectMapper objectMapper;

	@Autowired MemberService memberService;	

//	@Rule
//	public Exception exception = Exception.none();

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
					mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
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
					mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
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
					mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
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

}
