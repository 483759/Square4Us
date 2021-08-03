package com.ssafy.square4us.api.mvc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.request.MemberJoinPostReq;

import lombok.RequiredArgsConstructor;

/**
 * ReadOnly 달고 Create, Update, Delete는 따로 표기
 * */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

	private MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;
	
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	@Transactional(readOnly = true)
	public Member getMemberByEmail(String email) {
		Optional<Member> member = memberRepository.findByEmail(email);
		if(!member.isPresent())
			return null;
		
		return member.get();
	}

	@Override
	@Transactional
	public Member createMember(MemberJoinPostReq joinInfo) {
		Member member = new Member(joinInfo.getEmail(), joinInfo.getNickname(), passwordEncoder.encode(joinInfo.getPassword()));
		return memberRepository.save(member);
	}

	//임시 구현 테스트
	@Override
	@Transactional
	public Long updateMemberByEmail(MemberJoinPostReq updateInfo) {
		Optional<Member> member = memberRepository.findByEmail(updateInfo.getEmail());
		if(!member.isPresent())
			return null;
		
		member.get().setNickname(updateInfo.getNickname());
		member.get().setPassword(passwordEncoder.encode(updateInfo.getPassword()));
		memberRepository.save(member.get());
		
		return member.get().getId();		
	}
}
