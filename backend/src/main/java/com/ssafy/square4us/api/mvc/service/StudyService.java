package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudyService {
    StudyDTO createStudy(StudyDTO.CreatePostReq studyInfo, Member member);

    @Transactional
    Boolean joinStudy(Long studyId, Member member);

    @Transactional
    Boolean acceptStudyJoin(Long studyId, Long memberId, Member leader);

    Long findStudyLeader(Long studyId);

    StudyDTO findByStudyId(Long studyId);

    List<StudyDTO> findAllStudies();

    List<StudyDTO> findStudiesByMember(Long memberId);

    @Transactional
    Boolean delegateLeader(Long studyId, Long leaderId, Long memberId);

    @Transactional
    Boolean deleteByStudyId(String email, Long studyId);

    @Transactional
    Boolean resign(String email, Long studyId);

    PageImpl<StudyDTO> findStudiesWithPaging(Pageable pageable);
}
