package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.request.StudyCreatePostReq;

import java.util.List;

public interface StudyService {
    Study createStudy(StudyCreatePostReq studyInfo, Member member);

    List<Study> findAllStudies();
}
