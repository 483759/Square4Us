package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.entity.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class StudyRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QStudy qStudy = QStudy.study;
    QMember qMember = QMember.member;
    QStudyMember qStudyMember = QStudyMember.studyMember;

    public StudyRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Study.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Study> findAllStudy() {
        return jpaQueryFactory
                .select(qStudy)
                .from(qStudy)
                .where(qStudy.dismantleFlag.ne('T'))
                .fetch();
    }

    public PageImpl<Study> findStudiesWithPaging(Pageable pageable) {
        JPAQuery query = jpaQueryFactory
                .select(qStudy)
                .from(qStudy)
                .where(qStudy.dismantleFlag.ne('T'));

        Long totalCount = query.fetchCount();
        List<Study> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    public Study findByStudyId(Long studyId) {
        return jpaQueryFactory
                .select(qStudy)
                .from(qStudy)
                .where(qStudy.id.eq(studyId), qStudy.dismantleFlag.ne('T'))
                .fetchOne();
    }

    public StudyMember getStudyMemberByEmail(String email, Long studyId) {
        return jpaQueryFactory
                .select(qStudyMember)
                .from(qStudyMember)
                .join(qMember).on(qStudyMember.member.id.eq(qMember.id))
                .join(qStudy).on(qStudyMember.study.id.eq(qStudy.id))
                .where(qMember.email.eq(email), qStudy.id.eq(studyId))
                .fetchOne()
                ;
    }

    public Long deleteStudyById(Long studyId) {
        return jpaQueryFactory.update(qStudy)
                .where(qStudy.id.eq(studyId))
                .set(qStudy.dismantleFlag, 'T')
                .set(qStudy.dismantleDate, new Date(System.currentTimeMillis()))
                .execute();
    }
}
