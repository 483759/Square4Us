package com.ssafy.square4us;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.square4us.api.request.StudyCreatePostReq;

/**
 * 스터디 생성/입장/참여/수정/탈퇴 관련된 요청들을 테스트
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StudyControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	@WithMockUser(username = "ssafy@naver.com", roles = "USER")
	@Ignore
	// @Rollback
	public void createStudy() throws JsonProcessingException, Exception {
		StudyCreatePostReq studyInfo = new StudyCreatePostReq();
		studyInfo.setCategory("ENGLISH");
		studyInfo.setName("광개토익왕");

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/study").accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(studyInfo)).contentType(MediaType.APPLICATION_JSON));

		result.andDo(print())
				// .andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
