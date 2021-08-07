package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudyService {
    StudyDTO createStudy(StudyDTO.CreatePostReq studyInfo, Member member);

    StudyDTO findByStudyId(Long studyId);

    List<StudyDTO> findAllStudies();

    @Transactional
    boolean deleteByStudyId(String email, Long studyId);

    @Transactional
    boolean resign(String email, Long studyId);

    PageImpl<StudyDTO> findStudiesWithPaging(Pageable pageable);
}
