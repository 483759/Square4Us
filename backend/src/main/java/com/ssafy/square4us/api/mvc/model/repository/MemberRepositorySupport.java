package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.QMember;
import com.ssafy.square4us.api.mvc.model.entity.QStudy;
import com.ssafy.square4us.api.mvc.model.entity.QStudyMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QMember qMember = QMember.member;
    QStudyMember qStudyMember = QStudyMember.studyMember;
    QStudy qStudy = QStudy.study;

    public MemberRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    public Long updateByMemberEmail(Member member) {
        Long affectedRow = jpaQueryFactory.update(qMember)
                .where(qMember.email.eq(member.getEmail()))
                .set(qMember.nickname, member.getNickname())
                .set(qMember.profile_path, member.getProfile_path())
                .set(qMember.profile_name, member.getProfile_name())
                .execute();
        return affectedRow;
    }

    public List<MemberDTO> findMembersByStudy(Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(MemberDTO.class, qMember))
                .from(qStudyMember)
                .innerJoin(qStudyMember.member, qMember)
                .leftJoin(qStudyMember.study, qStudy)
                .where(qStudy.id.eq(studyId)).fetch();
    }

    public List<MemberDTO> findMembersToWaitingJoin(Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(MemberDTO.class, qMember))
                .from(qStudyMember)
                .innerJoin(qStudyMember.member, qMember)
                .leftJoin(qStudyMember.study, qStudy)
                .where(qStudy.id.eq(studyId), qStudyMember.accepted.eq('F')).fetch();
    }
}
