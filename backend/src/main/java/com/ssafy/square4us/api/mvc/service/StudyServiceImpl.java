package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import com.ssafy.square4us.api.mvc.model.repository.StudyMemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepo;
    private final StudyRepositorySupport studyRepositorySupport;
    private final StudyMemberRepository studyMemberRepo;

    @Override
    @Transactional
    public Study createStudy(Study.CreatePostReq studyInfo, Member member) {
        //스터디를 만들고 member를 팀장으로 설정하는 메서드
        Study study = studyRepo.save(
                Study.builder()
                        .category(studyInfo.getCategory())
                        .name(studyInfo.getName())
                        .dismantleFlag('F')
                        .build());
        StudyMember sm = new StudyMember('T', study, member);

        studyMemberRepo.save(sm);

        return study;
    }

    @Override
    public Study findByStudyId(Long studyId) {
        return studyRepositorySupport.findByStudyId(studyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Study> findAllStudies() {
        return studyRepositorySupport.findAllStudy();
    }

    @Override
    @Transactional
    public boolean deleteByStudyId(String email, Long studyId) {
        StudyMember sm = studyRepositorySupport.getStudyMemberByEmail(email, studyId);
        if (sm == null || sm.getLeader() != 'T') {
            return false;
        }

        Long affectedRow = studyRepositorySupport.deleteStudyById(studyId);
        return affectedRow != 0;
    }

    @Override
    public boolean resign(String email, Long studyId) {
        StudyMember sm = studyRepositorySupport.getStudyMemberByEmail(email, studyId);
        if (sm == null || sm.getLeader() != 'F') {
            return false;
        }

        //Long affectedRow =
        return true;
    }

}
