package com.ssafy.square4us.api.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private char run_flag = 'T';
    private FileDTO thumbnail;
    @JsonIgnore
    private StudyDTO study;


    @Builder
    public MeetingDTO(Meeting meeting) {
        this.id = meeting.getId();
        this.name = meeting.getName();
        this.run_flag = meeting.getRun_flag();
        this.study = new StudyDTO(meeting.getStudy());
        if(meeting.getThumbnail() != null) {
            this.thumbnail = new FileDTO(meeting.getThumbnail());
        } else {
            this.thumbnail = null;
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

        @Schema(description = "maximum", example = "15")
        private String name;

        @Schema(description = "run_flag", example = "T")
        private char run_flag = 'T';

        public EnterGetRes(Long id, String name, char run_flag) {
            this.id = id;
            this.name = name;
            this.run_flag = run_flag;
        }

        public static BasicResponseBody<EnterGetRes> of(Integer statusCode, String message, MeetingDTO meeting) {
            return BasicResponseBody.of(statusCode, message, new EnterGetRes(meeting.getId(), meeting.getName(), meeting.getRun_flag()));
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
