package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;
import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepository;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepositorySupport;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
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
    private final MeetingRepositorySupport meetingRepositorySupport;
    private final StudyRepository studyRepo;

    @Override
    @Transactional
    public MeetingDTO createMeeting(Long studyId, int maximum) {
        Optional<Study> study = studyRepo.findById(studyId);
        if (!study.isPresent()) {
            return null;
        }

        Meeting meeting = meetingRepo.save(
                Meeting.builder()
                        .study(study.get())
                        .maximum(maximum)
                        .run_flag('T')
                        .build()
        );
        return new MeetingDTO(meeting);
    }

    @Override
    @Transactional
    public MeetingDTO enterMeeting(Long meetingId) {
        Optional<Meeting> meeting = meetingRepo.findById(meetingId);
        if (!meeting.isPresent()) {
            return null;
        }

        return new MeetingDTO(meeting.get());
    }

    @Override
    @Transactional
    public List<MeetingDTO> findAllMeetings() {
        return meetingRepositorySupport.findAll();
    }
}
