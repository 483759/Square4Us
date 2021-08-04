package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import com.ssafy.square4us.api.request.MeetingCreatePostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepo;
    private final StudyRepository studyRepo;

    @Override
    @Transactional
    public Meeting createMeeting(Long studyId, Meeting.CreatePostReq meetingInfo) {
        Optional<Study> study = studyRepo.findById(studyId);
        if(!study.isPresent()){
            return null;
        }

        Meeting meeting = meetingRepo.save(
                Meeting.builder()
                        .study(study.get())
                        .thumbnailName(meetingInfo.getThumbnailName())
                        .thumbnailPath(meetingInfo.getThumbnailPath())
                        .maximum(meetingInfo.getMaximum())
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
