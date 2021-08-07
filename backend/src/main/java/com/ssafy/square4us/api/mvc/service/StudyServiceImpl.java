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
    public StudyDTO findByStudyId(Long studyId) {
        return studyRepositorySupport.findByStudyId(studyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudyDTO> findAllStudies() {
        return studyRepositorySupport.findAllStudy();
    }

    @Override
    public PageImpl<StudyDTO> findStudiesWithPaging(Pageable pageable) {
        return studyRepositorySupport.findStudiesWithPaging(pageable);
    }

    @Override
    @Transactional
    public boolean deleteByStudyId(String email, Long studyId) {
        StudyMemberDTO sm = studyRepositorySupport.getStudyMemberByEmail(email, studyId);
        if (sm == null || sm.getLeader() != 'T') {
            return false;
        }

        Long affectedRow = studyRepositorySupport.deleteStudyById(studyId);
        return affectedRow != 0;
    }

    @Override
    @Transactional
    public boolean resign(String email, Long studyId) {
        StudyMemberDTO sm = studyRepositorySupport.getStudyMemberByEmail(email, studyId);
        if (sm == null || sm.getLeader() != 'F') {
            return false;
        }
        return true;
    }

}
