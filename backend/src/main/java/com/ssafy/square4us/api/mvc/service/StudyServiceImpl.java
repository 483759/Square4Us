package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import com.ssafy.square4us.api.mvc.model.repository.StudyMemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import com.ssafy.square4us.api.request.StudyCreatePostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepo;
    private final StudyMemberRepository studyMemberRepo;

    @Override
    @Transactional
    public Study createStudy(StudyCreatePostReq studyInfo, Member member) {
        //스터디를 만들고 member를 팀장으로 설정하는 메서드
        Study study = studyRepo.save(
                Study.builder()
                        .category(studyInfo.getCategory())
                        .name(studyInfo.getName())
                        .build());
        StudyMember sm = new StudyMember('T', study, member);

        studyMemberRepo.save(sm);

        return study;
    }

    @Override
    public Study findByStudyId(Long studyId) {
        Optional<Study> study = studyRepo.findById(studyId);
        if(!study.isPresent()){
            return null;
        }
        return study.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Study> findAllStudies() {
        return studyRepo.findAll();
    }

}
