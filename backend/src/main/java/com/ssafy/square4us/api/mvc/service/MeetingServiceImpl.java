package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.FileRepository;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepository;
import com.ssafy.square4us.api.mvc.model.repository.MeetingRepositorySupport;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import com.ssafy.square4us.common.util.S3Util;
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

    private final MeetingRepository meetingRepo;
    private final MeetingRepositorySupport meetingRepositorySupport;
    private final StudyRepository studyRepo;
    private final FileRepository fileRepo;
    private final S3Util s3Util;

    @Override
    @Transactional
    public MeetingDTO createMeeting(Long studyId, int maximum, MultipartFile thumbnail) throws IOException {
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
            uploadThumbnail(meeting, thumbnail);
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
    public void updateThumbnail(Long meetingId, MultipartFile thumbnail) throws IOException {
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

    @Transactional(rollbackFor = IOException.class)
    public void uploadThumbnail(Meeting meeting, MultipartFile thumbnail) throws IOException {
        String path = "thumbnail";
        try {
            FileDTO fd = s3Util.upload(thumbnail, path);
            FileEntity fe = FileEntity.builder()
                    .member(null)
                    .article(null)
                    .meeting(meeting)
                    .filePath(fd.getFilePath())
                    .fileName(fd.getFileName())
                    .fileOriginName(fd.getFileOriginName())
                    .contentType(fd.getContentType())
                    .build();
            fe = fileRepo.save(fe);
            meeting.setThumbnail(fe);
        } catch(IOException e) {
            throw new IOException("파일 저장 실패!");
        }
    }

    @Transactional
    public void deletePrevThumbnail(Meeting meeting) {
        FileEntity prevThumbnail = meeting.getThumbnail();
        s3Util.delete(prevThumbnail);
        fileRepo.delete(prevThumbnail);
    }
}
