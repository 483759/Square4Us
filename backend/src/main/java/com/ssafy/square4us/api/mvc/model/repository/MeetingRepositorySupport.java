package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.entity.Meeting;
import com.ssafy.square4us.api.mvc.model.entity.QMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QMeeting qMeeting = QMeeting.meeting;

    public List<Meeting> findAll() {
        return jpaQueryFactory
                .select(qMeeting)
                .from(qMeeting)
                .fetch();
    }
}
