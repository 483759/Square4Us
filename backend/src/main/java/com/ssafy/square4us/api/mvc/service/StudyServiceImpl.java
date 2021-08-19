package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.dto.StudyMemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import com.ssafy.square4us.api.mvc.model.repository.FileRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyMemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepositorySupport;
import com.ssafy.square4us.common.util.S3Util;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepo;
    private final StudyRepositorySupport studyRepositorySupport;
    private final StudyMemberRepository studyMemberRepo;
    private final FileRepository fileRepo;
    private final S3Util s3Util;

    public StudyServiceImpl(StudyRepository studyRepo, StudyRepositorySupport studyRepositorySupport, StudyMemberRepository studyMemberRepo, FileRepository fileRepo, S3Util s3Util) {
        this.studyRepo = studyRepo;
        this.studyRepositorySupport = studyRepositorySupport;
        this.studyMemberRepo = studyMemberRepo;
        this.fileRepo = fileRepo;
        this.s3Util = s3Util;
    }

    @Override
    @Transactional
    public StudyDTO createStudy(StudyDTO.CreatePostReq studyInfo, Member member) {
        //스터디를 만들고 member를 팀장으로 설정하는 메서드
        Study study = studyRepo.save(
                Study.builder()
                        .category(studyInfo.getCategory())
                        .name(studyInfo.getName())
                        .dismantleFlag('F')
                        .build());
        StudyMember sm = new StudyMember('T', 'T', study, member);

        studyMemberRepo.save(sm);

        return new StudyDTO(study);
    }

    @Override
    @Transactional
    public Boolean joinStudy(Long studyId, Member member) {
        boolean exists = studyRepositorySupport.existStudyMember(studyId, member.getId());
        if (exists) {
            return false;
        }

        Optional<Study> study = studyRepo.findById(studyId);
        if (!study.isPresent()) {
            return false;
        }

        studyMemberRepo.save(new StudyMember('F', 'F', study.get(), member));

        return true;
    }

    @Override
    @Transactional
    public Boolean acceptStudyJoin(Long studyId, Long memberId, Member leader) {
        Optional<Study> study = studyRepo.findById(studyId);    //존재하는 스터디인지 확인
        if (!study.isPresent()) {
            return false;
        }
        if (study.get().getDismantleFlag() == 'T') {      //이미 해체한 스터디이면
            return true;
        }

        StudyMember leaderMember = studyMemberRepo.findByStudy_IdAndMember_Id(studyId, leader.getId());
        System.out.println(leaderMember);
        if (leaderMember == null || leaderMember.getLeader() != 'T') {
            return false;
        }

        StudyMember newMember = studyMemberRepo.findByStudy_IdAndMember_Id(studyId, memberId);
        System.out.println(newMember);
        if (newMember == null) {
            return false;
        }

        newMember.setAccepted('T');
        studyMemberRepo.save(newMember);
        return true;
    }

    @Override
    public List<StudyDTO> findStudiesByMember(Long memberId) {
        return studyRepositorySupport.findStudiesByMember(memberId);
    }

    @Override
    public Long findStudyLeader(Long studyId) {
        return studyRepositorySupport.findStudyLeader(studyId);
    }

    @Override
    public StudyDTO findByStudyId(Long studyId) {
        return studyRepositorySupport.findByStudyId(studyId);
    }

    @Override
    public List<StudyDTO> findAllStudies() {
        return studyRepositorySupport.findAllStudy();
    }

    @Override
    public PageImpl<StudyDTO> findStudiesWithPaging(Pageable pageable) {
        return studyRepositorySupport.findStudiesWithPaging(pageable);
    }

    @Override
    @Transactional
    public boolean rejectStudyJoin(Long studyId, Long memberId, Member leader) {
        Optional<Study> study = studyRepo.findById(studyId);    //존재하는 스터디인지 확인
        if (!study.isPresent()) {
            return false;
        }
        if (study.get().getDismantleFlag() == 'T') {      //이미 해체한 스터디이면
            return true;
        }

        StudyMember leaderMember = studyMemberRepo.findByStudy_IdAndMember_Id(studyId, leader.getId());
        if (leaderMember == null || leaderMember.getLeader() != 'T') {
            return false;
        }

        StudyMember newMember = studyMemberRepo.findByStudy_IdAndMember_Id(studyId, memberId);
        if (newMember == null) {
            return false;
        }

        studyMemberRepo.delete(newMember);
        return true;
    }

    @Override
    public Boolean isLeaderOfThisStudy(Long studyId, Long memberId) {
        StudyMember studyMem = studyMemberRepo.findByStudy_IdAndMember_Id(studyId, memberId);
        if(studyMem == null || studyMem.getLeader() == 'F') {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public void updateProfile(Long studyId, MultipartFile profile) throws Exception {
        Optional<Study> study = studyRepo.findById(studyId);
        if(!study.isPresent()) {
            throw new Exception("해당 스터디가 없다!!");
        }
        Study st = study.get();
        FileEntity prevProfile = st.getProfile();
        if(prevProfile != null) {
            s3Util.delete(prevProfile);
            fileRepo.delete(prevProfile);
        }
        String path = "studyProfile";
        try {
            FileDTO up = s3Util.upload(profile, path);
            FileEntity fe = FileEntity.builder()
                    .meeting(null)
                    .member(null)
                    .article(null)
                    .study(study.get())
                    .filePath(up.getFilePath())
                    .fileName(up.getFileName())
                    .fileOriginName(up.getFileOriginName())
                    .contentType(up.getContentType())
                    .build();
            fileRepo.save(fe);
        } catch(IOException e) {
            throw new IOException("파일 저장 실패!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProfile(Long studyId) throws Exception {
        Optional<Study> study = studyRepo.findById(studyId);
        if(!study.isPresent()) {
            throw new Exception("스터디가 없다!!");
        }
        deleteProfile(study.get());
    }

    @Override
    public PageImpl<StudyDTO> getStudyListWithSearchingAndPaging(Pageable pageable, String key, String word) {
        if(key.equals("category")) {
            return studyRepositorySupport.findStudiesWithPagingByCategory(word, pageable);
        }
        return studyRepositorySupport.findStudiesWithPagingByStudyName(word, pageable);
    }

    @Transactional
    public void deleteProfile(Study study) throws Exception {
        FileEntity fe = study.getProfile();
        if(fe == null) {
            throw new Exception("프사 없음!");
        }
        s3Util.delete(fe);
        fileRepo.delete(fe);
    }

    @Override
    @Transactional
    public Boolean delegateLeader(Long studyId, Long leaderId, Long memberId) {
        Long result = studyRepositorySupport.updateLeaderMember(studyId, leaderId, 'F');
        if (result == 0) {
            return false;
        }
        result = studyRepositorySupport.updateLeaderMember(studyId, memberId, 'T');
        if (result == 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteByStudyId(String email, Long studyId) {
        StudyMemberDTO sm = studyRepositorySupport.getStudyMemberByEmail(email, studyId);
        if (sm == null || sm.getLeader() != 'T') {
            return false;
        }

        Optional<Study> find = studyRepo.findById(studyId);
        if(!find.isPresent()) {
            return false;
        }
        Study study = find.get();
        FileEntity fe = study.getProfile();
        if(fe != null) {
            s3Util.delete(fe);
            fileRepo.delete(fe);
        }
        Long affectedRow = studyRepositorySupport.deleteStudyById(studyId);
        return affectedRow != 0;
    }

    @Override
    @Transactional
    public Boolean withdrawStudy(Long memberId, Long studyId) {
        return studyRepositorySupport.withdrawStudy(memberId, studyId) != 0;
    }

}
