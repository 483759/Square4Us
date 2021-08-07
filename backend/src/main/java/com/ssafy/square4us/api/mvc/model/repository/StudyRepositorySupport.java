package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.dto.StudyMemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.QMember;
import com.ssafy.square4us.api.mvc.model.entity.QStudy;
import com.ssafy.square4us.api.mvc.model.entity.QStudyMember;
import com.ssafy.square4us.api.mvc.model.entity.Study;
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

    public List<StudyDTO> findAllStudy() {
        return jpaQueryFactory
                .select(Projections.constructor(StudyDTO.class, qStudy))
                .from(qStudy)
                .where(qStudy.dismantleFlag.ne('T'))
                .fetch();
    }

    public PageImpl<StudyDTO> findStudiesWithPaging(Pageable pageable) {
        JPAQuery query = jpaQueryFactory
                .select(Projections.constructor(StudyDTO.class, qStudy))
                .from(qStudy)
                .where(qStudy.dismantleFlag.ne('T'));

        Long totalCount = query.fetchCount();
        List<StudyDTO> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    public StudyDTO findByStudyId(Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(StudyDTO.class, qStudy))
                .from(qStudy)
                .where(qStudy.id.eq(studyId), qStudy.dismantleFlag.ne('T'))
                .fetchOne();
    }

    public StudyMemberDTO getStudyMemberByEmail(String email, Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(StudyMemberDTO.class, qStudyMember))
                .from(qStudyMember)
                .innerJoin(qStudyMember.member, qMember)//.where(qMember.email.eq(email))
                //.join(qMember).on(qStudyMember.member.id.eq(qMember.id))
                .innerJoin(qStudyMember.study, qStudy)//.on(qStudyMember.study.id.eq(qStudy.id))
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
