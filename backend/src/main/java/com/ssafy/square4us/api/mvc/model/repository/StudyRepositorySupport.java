package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.entity.QStudy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class StudyRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QStudy qStudy = QStudy.study;

    public Long deleteStudyById(Long studyId) {
        return jpaQueryFactory.update(qStudy)
                .where(qStudy.id.eq(studyId))
                .set(qStudy.dismantle_flag, 'T')
                .set(qStudy.dismantle_date, new Date(System.currentTimeMillis()))
                .execute();
    }
}
