package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;

public interface MemberService {
	Member getMemberByEmail(String email);
}
