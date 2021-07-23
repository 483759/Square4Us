package com.ssafy.square4us.api.mvc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired MemberRepository memberRepository;
	
	@Override
	public Member getMemberByEmail(String email) {
		Optional<Member> member = memberRepository.findByEmail(email);
		if(!member.isPresent())
			return null;
		
		//System.out.println(member.get());
		return member.get();
	}
}
