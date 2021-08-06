package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;

import java.util.List;

public interface MeetingService {
    Meeting createMeeting(Long studyId, Meeting.CreatePostReq meetingInfo);
    Meeting enterMeeting(Long meetingId);
    List<Meeting> findAllMeetings();
}
