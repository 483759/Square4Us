package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.dto.MeetingDTO;
import com.ssafy.square4us.api.mvc.model.entity.QMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QMeeting qMeeting = QMeeting.meeting;

    public List<MeetingDTO> findAll() {
        return jpaQueryFactory
                .select(Projections.constructor(MeetingDTO.class, qMeeting))
                .from(qMeeting)
                .fetch();
    }

    public List<MeetingDTO> findMeetingByStudy(Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(MeetingDTO.class, qMeeting))
                .from(qMeeting)
                .where(qMeeting.study.id.eq(studyId))
                .fetch();
    }
}
