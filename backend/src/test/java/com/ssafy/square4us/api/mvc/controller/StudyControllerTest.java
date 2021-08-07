package com.ssafy.square4us.api.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.square4us.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = StudyController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
@MockBean(JpaMetamodelMappingContext.class)
class StudyControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        //this.mockMvc = MockMvcBuilders.standaloneSetup(new StudyController()).build();
    }

    @Test
    void create() {
    }

    @Test
    void readAll() {
        try {
            //ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders)
        } catch (Exception e) {

        }
    }

    @Test
    void getStudyById() {
    }

    @Test
    void resignStudy() {
    }

    @Test
    void deleteStudy() {
    }
}