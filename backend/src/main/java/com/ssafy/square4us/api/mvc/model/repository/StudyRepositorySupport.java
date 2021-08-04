package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QStudy qStudy = QStudy.study;
    QMember qMember = QMember.member;
    QStudyMember qStudyMember = QStudyMember.studyMember;

    public List<Study> findAllStudy() {
        return jpaQueryFactory
                .select(qStudy)
                .where(qStudy.dismantle_flag.ne('T'))
                .fetch();
    }

    public Study findByStudyId(Long studyId) {
        return jpaQueryFactory
                .select(qStudy)
                .from(qStudy)
                .where(qStudy.id.eq(studyId), qStudy.dismantle_flag.ne('T'))
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
                .set(qStudy.dismantle_flag, 'T')
                .set(qStudy.dismantle_date, new Date(System.currentTimeMillis()))
                .execute();
    }
}
