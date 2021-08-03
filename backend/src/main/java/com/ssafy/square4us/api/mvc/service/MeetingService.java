package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.request.MeetingCreatePostReq;

import java.util.List;

public interface MeetingService {
    Meeting createMeeting(MeetingCreatePostReq meetingInfo);

    List<Meeting> findAllMeetings();
}
