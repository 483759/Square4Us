package com.ssafy.square4us.api.mvc.model.entity;

import com.ssafy.square4us.api.response.BasicResponseBody;
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
    private char run_flag = 'T';

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    private Study study;

    @Builder
    public Meeting(String thumbnailName, String thumbnailPath, int maximum, char run_flag, Study study) {
        this.thumbnailName = thumbnailName;
        this.thumbnailPath = thumbnailPath;
        this.maximum = maximum;
        this.run_flag = run_flag;
        this.study = study;
    }

    @Getter
    @Schema(description = "Meeting Create Post Request")
    public static class CreatePostReq {
        @Schema(name = "maximum", example = "15")
        int maximum;

        @Schema(name = "thumbnailname", example = "sample.jpg")
        String thumbnailName;

        @Schema(name = "thumbnailpath", example = "/test/sample")
        String thumbnailPath;

        @Builder
        public CreatePostReq(int maximum, String thumbnailName, String thumbnailPath) {
            this.maximum = maximum;
            this.thumbnailName = thumbnailName;
            this.thumbnailPath = thumbnailPath;
        }
    }

    @Getter
    @Schema(description = "특정 미팅 입장 Response")
    public static class EnterGetRes extends BasicResponseBody {
        @Schema(description = "미팅 아이디", example = "2")
        private Long id;

        @Schema(description = "썸네일 사진 파일명", example = "sample.jpg")
        private String thumbnailName;

        @Schema(description = "썸네일 사진 파일", example = "/test/sample")
        private String thumbnailPath;

        @Schema(description = "maximum", example = "15")
        private int maximum = 4;

        @Schema(description = "run_flag", example = "T")
        private char run_flag = 'T';

        public EnterGetRes(Integer statusCode, String message, Long id, String thumbnailName, String thumbnailPath, int maximum, char run_flag) {
            super(statusCode, message);
            this.id = id;
            this.thumbnailName = thumbnailName;
            this.thumbnailPath = thumbnailPath;
            this.maximum = maximum;
            this.run_flag = run_flag;
        }

        public static EnterGetRes of(Integer statusCode, String message, Meeting meeting){
            return new EnterGetRes(statusCode, message, meeting.getId(), meeting.getThumbnailName(), meeting.getThumbnailPath(), meeting.getMaximum(), meeting.getRun_flag());
        }
    }
}
