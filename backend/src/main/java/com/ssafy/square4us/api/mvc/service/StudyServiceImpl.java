package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.dto.StudyMemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import com.ssafy.square4us.api.mvc.model.repository.StudyMemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepositorySupport;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepo;
    private final StudyRepositorySupport studyRepositorySupport;
    private final StudyMemberRepository studyMemberRepo;

    public StudyServiceImpl(StudyRepository studyRepo, StudyRepositorySupport studyRepositorySupport, StudyMemberRepository studyMemberRepo) {
        this.studyRepo = studyRepo;
        this.studyRepositorySupport = studyRepositorySupport;
        this.studyMemberRepo = studyMemberRepo;
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

        Long affectedRow = studyRepositorySupport.deleteStudyById(studyId);
        return affectedRow != 0;
    }

    @Override
    @Transactional
    public Boolean resign(String email, Long studyId) {
        StudyMemberDTO sm = studyRepositorySupport.getStudyMemberByEmail(email, studyId);
        if (sm == null || sm.getLeader() != 'F') {
            return false;
        }
        return true;
    }

}
