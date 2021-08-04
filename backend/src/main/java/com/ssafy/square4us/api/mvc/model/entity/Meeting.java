package com.ssafy.square4us.api.mvc.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Meeting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "thumbnail_name")
    private String thumbnailName;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "maximum")
    private int maximum = 4;

    @Column(name = "run_flag")
    private boolean run_flag = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    private Study study;

    @Builder
    public Meeting(Long id, String thumbnailName, String thumbnailPath, int maximum, boolean run_flag, Study study) {
        this.id = id;
        this.thumbnailName = thumbnailName;
        this.thumbnailPath = thumbnailPath;
        this.maximum = maximum;
        this.run_flag = run_flag;
        this.study = study;
    }

    @Getter
    @Schema(description = "Meeting Create Post Request")
    public static class CreatePostReq {

//        @Schema(name = "studyid", example = "1")
//        Long studyId;

        @Schema(name = "maximum", example = "15")
        Integer maximum;

        @Schema(name = "thumbnailname", example = "sample.jpg")
        String thumbnailName;

        @Schema(name = "thumbnailpath", example = "/test/sample")
        String thumbnailPath;

        @Builder
        public CreatePostReq(Integer maximum, String thumbnailName, String thumbnailPath) {
            //this.studyId = studyId;
            this.maximum = maximum;
            this.thumbnailName = thumbnailName;
            this.thumbnailPath = thumbnailPath;
        }
    }
}
