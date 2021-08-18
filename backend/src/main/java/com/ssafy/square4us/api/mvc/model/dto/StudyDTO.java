package com.ssafy.square4us.api.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyDTO {
    private Long id;
    private String category;
    private String name;
    @JsonIgnore
    private char dismantleFlag = 'F';
    @JsonIgnore
    private Date dismantleDate;
    private FileDTO profile;

    @Builder
    public StudyDTO(Long id, String category, String name, char dismantleFlag, Date dismantleDate, FileDTO profile) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.dismantleFlag = dismantleFlag;
        this.dismantleDate = dismantleDate;
        this.profile = profile;
    }

    public StudyDTO(Study study) {
        this.id = study.getId();
        this.category = study.getCategory();
        this.name = study.getName();
        this.dismantleFlag = study.getDismantleFlag();
        this.dismantleDate = study.getDismantleDate();
        if(study.getProfile() != null) {
            this.profile = new FileDTO(study.getProfile());
        } else {
            this.profile = null;
        }
    }

    @Getter
    public static class CreatePostReq {
        @Schema(name = "category", example = "ALGORITHM")
        String category;
        @Schema(name = "name", example = "모르고리즘")
        String name;

        public CreatePostReq(String category, String name) {
            this.category = category;
            this.name = name;
        }

        public BasicResponseBody<StudyDTO.CreatePostReq> of(Integer statusCode, String message, String category, String name) {
            return BasicResponseBody.of(statusCode, message, new StudyDTO.CreatePostReq(category, name));
        }
    }

    @Getter
    public static class InfoGetRes {
        private Long id;
        private String category;
        private String name;
        private Long leaderId;
        private FileDTO profile;

        public InfoGetRes(Long id, String category, String name, Long leaderId, FileDTO profile) {
            this.id = id;
            this.category = category;
            this.name = name;
            this.leaderId = leaderId;
            this.profile = profile;
        }

        public static BasicResponseBody<StudyDTO.InfoGetRes> of(Integer statusCode, String message, Long id, String category, String name, Long leaderId, FileDTO profile) {
            return BasicResponseBody.of(statusCode, message, new StudyDTO.InfoGetRes(id, category, name, leaderId, profile));
        }
    }

    @Getter
    public static class PageableListGetRes {
        Page<StudyDTO> studyList;

        public PageableListGetRes(Page<StudyDTO> studyList) {
            this.studyList = studyList;
        }

        public static BasicResponseBody<PageableListGetRes> of(Integer statusCode, String message, Page<StudyDTO> studyList) {
            return BasicResponseBody.of(statusCode, message, new PageableListGetRes(studyList));
        }
    }

    @Getter
    public static class ListGetRes {
        List<StudyDTO> studyList;

        public ListGetRes(List<StudyDTO> studyList) {
            this.studyList = studyList;
        }

        public static BasicResponseBody<StudyDTO.ListGetRes> of(Integer statusCode, String message, List<StudyDTO> studyList) {
            return BasicResponseBody.of(statusCode, message, new StudyDTO.ListGetRes(studyList));
        }
    }

    @Getter
    public static class LeaderFlagGetRes {
        Boolean flag;

        public LeaderFlagGetRes(Boolean flag) { this.flag = flag; }

        public static BasicResponseBody<StudyDTO.LeaderFlagGetRes> of(Integer statusCode, String message, Boolean flag) {
            return BasicResponseBody.of(statusCode, message, flag);
        }
    }
}
