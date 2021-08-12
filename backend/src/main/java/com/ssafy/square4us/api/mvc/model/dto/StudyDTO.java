package com.ssafy.square4us.api.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
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
    private List<FileDTO> files;

    @Builder
    public StudyDTO(Long id, String category, String name, char dismantleFlag, Date dismantleDate, List<FileDTO> files) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.dismantleFlag = dismantleFlag;
        this.dismantleDate = dismantleDate;
        this.files = files;
    }

    public StudyDTO(Study study) {
        this.id = study.getId();
        this.category = study.getCategory();
        this.name = study.getName();
        this.dismantleFlag = study.getDismantleFlag();
        this.dismantleDate = study.getDismantleDate();
        if(study.getFiles() != null) {
            this.files = new ArrayList<>();
            for(FileEntity fe: study.getFiles()) {
                this.files.add(new FileDTO(fe));
            }
        } else {
            this.files = null;
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
        private List<FileDTO> files;

        public InfoGetRes(Long id, String category, String name, Long leaderId, List<FileDTO> files) {
            this.id = id;
            this.category = category;
            this.name = name;
            this.leaderId = leaderId;
            this.files = files;
        }

        public static BasicResponseBody<StudyDTO.InfoGetRes> of(Integer statusCode, String message, Long id, String category, String name, Long leaderId, List<FileDTO> files) {
            return BasicResponseBody.of(statusCode, message, new StudyDTO.InfoGetRes(id, category, name, leaderId, files));
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
}
