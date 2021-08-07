package com.ssafy.square4us.api.mvc.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.repository.*;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.api.mvc.service.StudyService;
import com.ssafy.square4us.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = StudyController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
class StudyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyService studyService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private StudyRepository studyRepository;
    @MockBean
    private StudyRepositorySupport studyRepositorySupport;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private MemberRepositorySupport memberRepositorySupport;
    @MockBean
    private JPAQueryFactory jpaQueryFactory;
    @MockBean
    private StudyMemberRepository studyMemberRepository;


    @BeforeEach
    void setUp() {
        //StudyService studyService = new StudyServiceImpl(StudyRepository.class, new StudyRepositorySupport(new JPAQueryFactory()), StudyMemberRepository.class);
        //this.mockMvc = MockMvcBuilders.standaloneSetup(new StudyController()).build();
    }

    @Test
    @Disabled
    void create() {
    }

    @Test
    //@Disabled
    @WithMockUser
    void readAll() throws Exception {
        int page = 0;
        int size = 2;

        ResultActions result = this.mockMvc.perform(
                //MockMvcRequestBuilders.get("/api/study" + "?page=" + page + "&size=" + size, new Object[0])
                MockMvcRequestBuilders.get("/api/study?page=0&size=2", new Object[0])
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON})
                        .contentType(MediaType.APPLICATION_JSON));
        result.andDo(
                MockMvcResultHandlers.print()
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Disabled
    void getStudyById() {
    }

    @Test
    @Disabled
    void resignStudy() {
    }

    @Test
    @Disabled
    void deleteStudy() {
    }
}