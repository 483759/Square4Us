package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;

import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(Long studyId, int maximum);

    MeetingDTO enterMeeting(Long meetingId);

    List<MeetingDTO> findAllMeetings();
}
