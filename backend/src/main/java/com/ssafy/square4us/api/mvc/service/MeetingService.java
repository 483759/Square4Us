package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(Long studyId, int maximum, MultipartFile thumbnail);

    MeetingDTO enterMeeting(Long meetingId);

    List<MeetingDTO> findAllMeetings();

    void updateThumbnail(Long meetingId, MultipartFile thumbnail);

    void deleteThumbnailById(Long meetingId);
}
