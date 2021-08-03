package com.ssafy.square4us.api.response;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Meeting Create Post Response")
public class MeetingCreatePostRes extends BasicResponseBody {
    @Schema(name = "스터디 ID")
    Long id;

    public static MeetingCreatePostRes of(Integer statusCode, String message, Meeting meet) {
        MeetingCreatePostRes res = new MeetingCreatePostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(meet.getId());
        return res;
    }
}
