package com.ssafy.square4us.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;

@Component
public class MemberDetailsService implements UserDetailsService {
	@Autowired
	MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Member member = memberService.getMemberByEmail(userName);
		if (member == null) {
			return null;
		}

		return new MemberDetails(member);
	}
}
