package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingDTO {
    private Long id;
    private String thumbnailName;
    private String thumbnailPath;
    private int maximum = 4;
    private char run_flag = 'T';
    private StudyDTO study;

    @Builder
    public MeetingDTO(Meeting meeting) {
        this.thumbnailName = meeting.getThumbnailName();
        this.thumbnailPath = meeting.getThumbnailPath();
        this.maximum = meeting.getMaximum();
        this.run_flag = meeting.getRun_flag();
        this.study = new StudyDTO(meeting.getStudy());
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
    public static class CreatePostRes {
        Long id;

        public CreatePostRes(Long id) {
            this.id = id;
        }

        public static BasicResponseBody<CreatePostRes> of(Integer statusCode, String message, Long id) {
            return BasicResponseBody.of(statusCode, message, new CreatePostRes(id));
        }
    }

    @Getter
    @Schema(description = "특정 미팅 입장 Response")
    public static class EnterGetRes {
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

        public EnterGetRes(Long id, String thumbnailName, String thumbnailPath, int maximum, char run_flag) {
            this.id = id;
            this.thumbnailName = thumbnailName;
            this.thumbnailPath = thumbnailPath;
            this.maximum = maximum;
            this.run_flag = run_flag;
        }

        public static BasicResponseBody<EnterGetRes> of(Integer statusCode, String message, MeetingDTO meeting) {
            return BasicResponseBody.of(statusCode, message, new EnterGetRes(meeting.getId(), meeting.getThumbnailName(), meeting.getThumbnailPath(), meeting.getMaximum(), meeting.getRun_flag()));
        }
    }

    @Getter
    @Schema(description = "All Meeting List Get Response")
    public static class ListGetRes {
        List<MeetingDTO> meetings;

        public ListGetRes(List<MeetingDTO> meetings) {
            this.meetings = meetings;
        }

        public static BasicResponseBody<ListGetRes> of(Integer statusCode, String message, List<MeetingDTO> meetingList) {
            return BasicResponseBody.of(statusCode, message, new ListGetRes(meetingList));
        }
    }

}
