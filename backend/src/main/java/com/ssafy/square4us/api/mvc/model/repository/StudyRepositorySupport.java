package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.entity.QStudy;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QStudy qStudy = QStudy.study;

//    public Study findByStudyName(String studyName){
//        Study study = jpaQueryFactory.selectOne()
//                .where(qStudy.name.eq(studyName))
//                .fro;
//    }
}
