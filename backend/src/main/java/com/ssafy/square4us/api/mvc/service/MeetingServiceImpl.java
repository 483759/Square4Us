package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.FileRepository;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepository;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepositorySupport;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingServiceImpl implements MeetingService {

    private final String THUMBNAIL_PATH = System.getProperty("user.dir") + "\\thumbnail";

    private final MeetingRepository meetingRepo;
    private final MeetingRepositorySupport meetingRepositorySupport;
    private final StudyRepository studyRepo;
    private final FileRepository fileRepo;

    @Override
    @Transactional
    public MeetingDTO createMeeting(Long studyId, int maximum, MultipartFile thumbnail) {
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

        if(thumbnail != null) {
            int code = uploadThumbnail(meeting, thumbnail);
            if(code == 1) {
                return null;
            }
        }
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

    @Override
    public List<MeetingDTO> findMeetingsByStudy(Long studyId) {
        return meetingRepositorySupport.findMeetingByStudy(studyId);
    }

    @Override
    public void updateThumbnail(Long meetingId, MultipartFile thumbnail) {
        Optional<Meeting> find = meetingRepo.findById(meetingId);
        if(!find.isPresent()) {
            return;
        }
        Meeting meeting = find.get();
        if(meeting.getThumbnail() != null) {
            deletePrevThumbnail(meeting);
        }
        uploadThumbnail(meeting, thumbnail);
    }

    @Override
    public void deleteThumbnailById(Long meetingId) {
        Optional<Meeting> find = meetingRepo.findById(meetingId);
        if(!find.isPresent()) {
            return;
        }
        Meeting meeting = find.get();
        deletePrevThumbnail(meeting);
    }

    @Transactional
    public int uploadThumbnail(Meeting meeting, MultipartFile thumbnail) {
        File path = new File(THUMBNAIL_PATH);
        if(!path.exists()) {
            path.mkdir();
        }
        String originName = thumbnail.getOriginalFilename();
        String contentType = thumbnail.getContentType();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + originName.substring(originName.lastIndexOf('.'));

        FileEntity file = FileEntity.builder()
                .member(null)
                .study(null)
                .article(null)
                .meeting(meeting)
                .filePath(THUMBNAIL_PATH)
                .fileName(saveFileName)
                .fileOriginName(originName)
                .contentType(contentType)
                .build();

        file = fileRepo.save(file);

        try {
            thumbnail.transferTo(new File(THUMBNAIL_PATH, saveFileName));
        } catch (IOException e) {
            return 1;
        }
        meeting.setThumbnail(file);
        return 2;
    }

    @Transactional
    public void deletePrevThumbnail(Meeting meeting) {
        FileEntity prevThumbnail = meeting.getThumbnail();
        File thumb = new File(prevThumbnail.getFilePath(), prevThumbnail.getFileName());
        if(thumb.exists()) {
            thumb.delete();
        }
        fileRepo.delete(prevThumbnail);
    }
}
