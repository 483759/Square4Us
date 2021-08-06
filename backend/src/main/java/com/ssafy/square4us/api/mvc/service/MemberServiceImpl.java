package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepositorySupport;
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
    public Member createMember(Member.JoinPostReq joinInfo) {
        Member member = Member.builder()
                .email(joinInfo.getEmail())
                .nickname(joinInfo.getNickname())
                .password(new BCryptPasswordEncoder().encode(joinInfo.getPassword()))
                .build();
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Long updateMemberByEmail(String email, Member.UpdatePatchReq updateInfo) {
        Member member = Member.builder().email(email)
                .nickname(updateInfo.getNickname())
                .profile_name(updateInfo.getProfile_name())
                .profile_path(updateInfo.getProfile_path())
                .build();
        return memberRepositorySupport.updateByMemberEmail(member);
    }

    @Override
    @Transactional
    public void deleteMemberByEmail(String email) {
        memberRepository.deleteByEmail(email);
    }
}
