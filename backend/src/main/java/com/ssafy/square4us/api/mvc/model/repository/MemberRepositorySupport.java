package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.QMember;
import com.ssafy.square4us.api.mvc.model.entity.QStudy;
import com.ssafy.square4us.api.mvc.model.entity.QStudyMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {
    @PersistenceContext
    EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    QMember qMember = QMember.member;
    QStudyMember qStudyMember = QStudyMember.studyMember;
    QStudy qStudy = QStudy.study;

    public MemberRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    public Long updateByMemberEmail(Long memberId, MemberDTO.UpdatePatchReq updateInfo) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(entityManager, qMember);
        Long affectedRow = jpaUpdateClause
                .where(qMember.id.eq(memberId))
                .set(qMember.nickname, updateInfo.getNickname())
                //.set(qMember.profile, member.getProfile())
                .execute();
//        Long affectedRow = jpaQueryFactory
//                .where(qMember.id.eq(member.getId()))
//                .set(qMember.nickname, member.getNickname())
//                .set(qMember.profile, member.getProfile())
//                .execute();
        return affectedRow;
    }

    public List<MemberDTO> findMembersByStudy(Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(MemberDTO.class, qMember))
                .from(qStudyMember)
                .innerJoin(qStudyMember.member, qMember)
                .leftJoin(qStudyMember.study, qStudy)
                .where(qStudy.id.eq(studyId), qStudyMember.accepted.eq('T')).fetch();
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
