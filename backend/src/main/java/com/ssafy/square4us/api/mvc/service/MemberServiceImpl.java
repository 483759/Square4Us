package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.MemberRole;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepositorySupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ReadOnly 달고 Create, Update, Delete는 따로 표기
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberRepositorySupport memberRepositorySupport;

    public MemberServiceImpl(MemberRepository memberRepository, MemberRepositorySupport memberRepositorySupport) {
        this.memberRepository = memberRepository;
        this.memberRepositorySupport = memberRepositorySupport;
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (!member.isPresent())
            return null;

        return member.get();
    }

    @Override
    public MemberDTO getMemberDTOByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (!member.isPresent())
            return null;

        return new MemberDTO(member.get());
    }

    @Override
    public List<MemberDTO> getMembersByStudy(Long studyId) {
        return memberRepositorySupport.findMembersByStudy(studyId);
    }

    @Override
    public List<MemberDTO> getMembersWaitJoin(Long studyId) {
        return memberRepositorySupport.findMembersToWaitingJoin(studyId);
    }

    @Override
    @Transactional
    public MemberDTO createMember(MemberDTO.JoinPostReq joinInfo) {
        Member member = Member.builder()
                .email(joinInfo.getEmail())
                .nickname(joinInfo.getNickname())
                .role(MemberRole.USER)
                .password(new BCryptPasswordEncoder().encode(joinInfo.getPassword()))
                .build();
        return new MemberDTO(memberRepository.save(member));
    }

    @Override
    @Transactional
    public Long updateMemberByEmail(String email, MemberDTO.UpdatePatchReq updateInfo) {
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
