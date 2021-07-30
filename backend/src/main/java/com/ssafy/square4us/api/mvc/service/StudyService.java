package com.ssafy.square4us.api.mvc.service;

import java.util.List;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.request.StudyCreatePostReq;

public interface StudyService {
	Study createStudy(StudyCreatePostReq studyInfo, Member member);
	List<Study> findAllStudies();
}