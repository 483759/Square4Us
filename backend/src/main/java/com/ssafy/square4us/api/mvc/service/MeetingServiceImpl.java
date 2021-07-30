package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepository;
import com.ssafy.square4us.api.request.MeetingCreatePostReq;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

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
                        .maxPeople(meetingInfo.getMaxPeople());
        )
        return meeting;
    }

    @Override
    @Transactional
    public List<Meeting> findAllMeetings() {
        return meetingRepo.findAll();
    }
}
