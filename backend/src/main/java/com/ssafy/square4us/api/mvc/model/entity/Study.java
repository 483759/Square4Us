package com.ssafy.square4us.api.mvc.model.entity;

import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Study extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    private String category;

    @Column(name = "study_name")
    private String name;

    @Column(name = "dismantle_flag")
    private char dismantleFlag = 'F';

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dismantle_date")
    private Date dismantleDate;

    @Builder
    public Study(Long id, String category, String name, char dismantleFlag, Date dismantleDate) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.dismantleFlag = dismantleFlag;
        this.dismantleDate = dismantleDate;
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

        public BasicResponseBody<CreatePostReq> of(Integer statusCode, String message, String category, String name) {
            return BasicResponseBody.of(statusCode, message, new CreatePostReq(category, name));
        }
    }

    @Getter
    public static class InfoGetRes {
        private Long id;
        private String category;
        private String name;
        private char dismantleFlag;
        private Date dismantleDate;

        public InfoGetRes(Long id, String category, String name, char dismantleFlag, Date dismantleDate) {
            this.id = id;
            this.category = category;
            this.name = name;
            this.dismantleFlag = dismantleFlag;
            this.dismantleDate = dismantleDate;
        }

        public static BasicResponseBody<InfoGetRes> of(Integer statusCode, String message, Long id, String category, String name, char dismantleFlag, Date dismantleDate) {
            return BasicResponseBody.of(statusCode, message, new InfoGetRes(id, category, name, dismantleFlag, dismantleDate));
        }
    }

    @Getter
    public static class ListGetRes {
        Page<Study> studyList;

        public ListGetRes(Page<Study> studyList) {
            this.studyList = studyList;
        }

        public static BasicResponseBody<Study.ListGetRes> of(Integer statusCode, String message, Page<Study> studyList) {
            return BasicResponseBody.of(statusCode, message, new Study.ListGetRes(studyList));
        }
    }
}
