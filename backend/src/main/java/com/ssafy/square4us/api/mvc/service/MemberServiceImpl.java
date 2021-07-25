package com.ssafy.square4us.api.mvc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.request.MemberJoinPostReq;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired MemberRepository memberRepository;
	@Autowired PasswordEncoder passwordEncoder;
	
	@Override
	public Member getMemberByEmail(String email) {
		Optional<Member> member = memberRepository.findByEmail(email);
		if(!member.isPresent())
			return null;
		
		return member.get();
	}

	@Override
	public Member createMember(MemberJoinPostReq joinInfo) {
		Member member = new Member(joinInfo.getEmail(), joinInfo.getNickname(), passwordEncoder.encode(joinInfo.getPassword()));
		return memberRepository.save(member);
	}
}
