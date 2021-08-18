package com.ssafy.square4us.api.mvc.model.repository;

import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    StudyMember findByStudy_IdAndMember_Id(Long studyId, Long memberId);
    @Query("select count(sm) from StudyMember sm where sm.leader='T' and sm.member.id=:memberId")
    Long existsLeaderJoinStudy(Long memberId);
}
