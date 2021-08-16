package com.ssafy.square4us.api.mvc.model.repository;

import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    StudyMember findByStudy_IdAndMember_Id(Long studyId, Long memberId);
    Boolean existsStudyMemberByMember_Id(Long memberId);
    Boolean findByStudy_IdAndMember_Email(Long studyId, String email);
}
