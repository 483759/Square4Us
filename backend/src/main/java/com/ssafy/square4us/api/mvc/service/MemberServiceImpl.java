package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.MemberRole;
import com.ssafy.square4us.api.mvc.model.repository.FileRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepositorySupport;
import com.ssafy.square4us.api.mvc.model.repository.StudyMemberRepository;
import com.ssafy.square4us.common.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * ReadOnly 달고 Create, Update, Delete는 따로 표기
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final String PROFILE_PATH = System.getProperty("user.dir") + "\\profile";

    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepo;
    private final MemberRepositorySupport memberRepositorySupport;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileRepository fileRepository;
    private final S3Util s3Util;

    public MemberServiceImpl(MemberRepository memberRepository, StudyMemberRepository studyMemberRepo, MemberRepositorySupport memberRepositorySupport, FileRepository fileRepository, S3Util s3Util) {
        this.memberRepository = memberRepository;
        this.studyMemberRepo = studyMemberRepo;
        this.memberRepositorySupport = memberRepositorySupport;
        this.fileRepository = fileRepository;
        this.s3Util = s3Util;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
    @Transactional(rollbackFor = IOException.class)
    public MemberDTO createMember(MemberDTO.JoinPostReq joinInfo) {
        Member member = Member.builder()
                .email(joinInfo.getEmail())
                .nickname(joinInfo.getNickname())
                .role(MemberRole.USER)
                .password(passwordEncoder.encode(joinInfo.getPassword()))
                .build();
        member = memberRepository.save(member);

        return new MemberDTO(member);
    }

    @Override
    @Transactional
    public Long updateMemberByEmail(Long memberId, MemberDTO.UpdatePatchReq updateInfo) {
        return memberRepositorySupport.updateByMemberEmail(memberId, updateInfo);
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public MemberDTO updateProfileByEmail(String email, MultipartFile profile) throws IOException {
        Optional<Member> find = memberRepository.findByEmail(email);
        if(!find.isPresent()) {
            return null;
        }
        Member member = find.get();
        deletePrevProfile(member);

        uploadProfile(member, profile);

        return new MemberDTO(member);
    }

    @Override
    @Transactional
    public MemberDTO deleteProfileByEmail(String email) {
        Optional<Member> find = memberRepository.findByEmail(email);
        if(!find.isPresent()) {
            return null;
        }
        Member member = find.get();
        deletePrevProfile(member);
        if(member.getProfile() != null) {
            fileRepository.deleteById(member.getProfile().getId());
        }

        return new MemberDTO(member);
    }


    @Override
    @Transactional
    public Boolean deleteMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(!member.isPresent()){
            return false;
        }

        if(studyMemberRepo.existsLeaderJoinStudy(member.get().getId())!=0){
            return false;
        }

        deleteProfileByEmail(email);
        memberRepository.deleteByEmail(email);
        return true;
    }

    @Transactional
    public MemberDTO deletePrevProfile(Member member) {
        FileEntity prevProfile = member.getProfile();
        if(prevProfile != null) {
            s3Util.delete(prevProfile);
            fileRepository.deleteById(prevProfile.getId());
        }
        member.setProfile(null);
        return new MemberDTO(member);
    }

    @Transactional
    public void uploadProfile(Member member, MultipartFile profile) throws IOException {
        String path = "profile";
        try {
            FileDTO fd = s3Util.upload(profile, path);
            FileEntity file = FileEntity.builder()
                    .member(member)
                    .article(null)
                    .meeting(null)
                    .filePath(fd.getFilePath())
                    .fileName(fd.getFileName())
                    .fileOriginName(fd.getFileOriginName())
                    .contentType(fd.getContentType())
                    .build();

            file = fileRepository.save(file);
            member.setProfile(file);
        } catch (IOException e) {
            throw new IOException("파일 저장 실패!");
        }
    }
}
