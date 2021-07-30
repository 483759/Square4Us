package com.ssafy.square4us.api.response;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "All Meeting List Get Response")
public class MeetingListGetRes extends BasicResponseBody {
    @Schema(name = "미팅 목록")
    List<Meeting> meetingList;

    public static MeetingListGetRes of(Integer statusCode, String message, List<Meeting> meetingList) {
        MeetingListGetRes res = new MeetingListGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setMeetingList(meetingList);
        return res;
    }
}
