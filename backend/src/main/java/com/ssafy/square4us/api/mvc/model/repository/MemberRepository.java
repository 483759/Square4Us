package com.ssafy.square4us.api.mvc.model.repository;

import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    //@Query("select m from Member m join fetch StudyMember sm join fetch Study s where s.id = :studyId")
    //@Query("select map from Member m join fetch StudyMember sm join fetch Study s on sm.member.id = m.id and sm.study.id = s.id where s.id = :studyId")
    //List<MemberDTO> findMembersByStudy(Long studyId);
    @Query("select m from Member m where m.id = :memberId")
    MemberDTO findMemberDTOById(Long memberId);
    @Transactional
    void deleteByEmail(String email);
}
