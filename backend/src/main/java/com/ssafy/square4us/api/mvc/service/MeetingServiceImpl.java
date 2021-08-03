package com.ssafy.square4us.api.mvc.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepository;
import com.ssafy.square4us.api.request.MeetingCreatePostReq;

@Service
@Transactional(readOnly = true)
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepo;

    @Override
    @Transactional
    public Meeting createMeeting(MeetingCreatePostReq meetingInfo) {
        Meeting meeting = meetingRepo.save(
                Meeting.builder()
                        .studyId(meetingInfo.getStudyId())
                        .thumbnailName(meetingInfo.getThumbnailName())
                        .thumbnailPath(meetingInfo.getThumbnailPath())
                        .maxPeople(meetingInfo.getMaxPeople())
                        .build()
        );
        return meeting;
    }

    @Override
    @Transactional
    public List<Meeting> findAllMeetings() {
        return meetingRepo.findAll();
    }
}
