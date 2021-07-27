package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.request.MemberJoinPostReq;

public interface MemberService {
	Member getMemberByEmail(String email);
	Member createMember(MemberJoinPostReq joinInfo);
	Long updateMemberByEmail(MemberJoinPostReq updateInfo);
}
