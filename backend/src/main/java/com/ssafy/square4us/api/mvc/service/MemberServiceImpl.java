package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepositorySupport;
import com.ssafy.square4us.api.request.MemberJoinPostReq;
import com.ssafy.square4us.api.request.MemberUpdatePatchReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ReadOnly 달고 Create, Update, Delete는 따로 표기
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberRepositorySupport memberRepositorySupport;
//	private PasswordEncoder passwordEncoder;
//	
//	public MemberServiceImpl(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//		passwordEncoder = new BCryptPasswordEncoder();
//	}

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (!member.isPresent())
            return null;

        return member.get();
    }

    @Override
    @Transactional
    public Member createMember(MemberJoinPostReq joinInfo) {
        //Member member = new Member(joinInfo.getEmail(), joinInfo.getNickname(), new BCryptPasswordEncoder().encode(joinInfo.getPassword()));
        Member member = Member.builder()
                .email(joinInfo.getEmail())
                .nickname(joinInfo.getNickname())
                .password(new BCryptPasswordEncoder().encode(joinInfo.getPassword()))
                .build();
        return memberRepository.save(member);
    }

    //임시 구현 테스트
    @Override
    @Transactional
    public Long updateMemberByEmail(String email, MemberUpdatePatchReq updateInfo) {
        Member member = Member.builder().email(email)
                .nickname(updateInfo.getNickname())
                .profile_name(updateInfo.getProfile_name())
                .profile_path(updateInfo.getProfile_path())
                .build();
//        member.setNickname(updateInfo.getNickname());
//        member.setProfile_name(updateInfo.getProfile_name());
//        member.setProfile_path(updateInfo.getProfile_path());
//        memberRepository.save(member);
        return memberRepositorySupport.updateByMemberEmail(member);
    }
}
