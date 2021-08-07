package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.QMember;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class MemberRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QMember qMember = QMember.member;

    public MemberRepositorySupport(JPAQueryFactory jpaQueryFactory) {
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
}
