package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    Boolean withdrawStudy(Long memberId, Long studyId);

    PageImpl<StudyDTO> findStudiesWithPaging(Pageable pageable);

    @Transactional
    boolean rejectStudyJoin(Long studyId, Long memberId, Member leader);

    @Transactional
    Boolean isLeaderOfThisStudy(Long studyId, Long memberId);

    @Transactional
    void updateProfile(Long studyId, MultipartFile profile) throws Exception;

    @Transactional
    void deleteProfile(Long studyId) throws Exception;

    @Transactional
    PageImpl<StudyDTO> getStudyListWithSearchingAndPaging(Pageable pageable, String key, String word);
}
