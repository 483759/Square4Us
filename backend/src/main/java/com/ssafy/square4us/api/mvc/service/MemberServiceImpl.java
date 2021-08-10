package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.MemberRole;
import com.ssafy.square4us.api.mvc.model.repository.FileRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepositorySupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ReadOnly 달고 Create, Update, Delete는 따로 표기
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final String PROFILE_PATH = System.getProperty("user.dir") + "\\profile";

    private final MemberRepository memberRepository;
    private final MemberRepositorySupport memberRepositorySupport;
    private final FileRepository fileRepository;

    public MemberServiceImpl(MemberRepository memberRepository, MemberRepositorySupport memberRepositorySupport, FileRepository fileRepository) {
        this.memberRepository = memberRepository;
        this.memberRepositorySupport = memberRepositorySupport;
        this.fileRepository = fileRepository;
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
    public MemberDTO createMember(MemberDTO.JoinPostReq joinInfo, MultipartFile profile) {
        Member member = Member.builder()
                .email(joinInfo.getEmail())
                .nickname(joinInfo.getNickname())
                .role(MemberRole.USER)
                .password(new BCryptPasswordEncoder().encode(joinInfo.getPassword()))
                .build();
        member = memberRepository.save(member);
        if(profile != null) {

            int code = uploadProfile(member, profile);
            if(code == 1) {
                return null;
            }
        }

        return new MemberDTO(member);
    }

    @Override
    @Transactional
    public Long updateMemberByEmail(String email, MemberDTO.UpdatePatchReq updateInfo) {
        Member member = Member.builder().email(email)
                .nickname(updateInfo.getNickname())
                .build();
        return memberRepositorySupport.updateByMemberEmail(member);
    }

    @Override
    @Transactional
    public MemberDTO updateProfileByEmail(String email, MultipartFile profile) {
        Optional<Member> find = memberRepository.findByEmail(email);
        if(!find.isPresent()) {
            return null;
        }
        Member member = find.get();
        deletePrevProfile(member);

        int code = uploadProfile(member, profile);
        if(code == 1) {
            return null;
        }

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
    public void deleteMemberByEmail(String email) {
        deleteProfileByEmail(email);
        memberRepository.deleteByEmail(email);
    }

    @Transactional
    public MemberDTO deletePrevProfile(Member member) {
        FileEntity prevProfile = member.getProfile();
        if(prevProfile != null) {
            File del = new File(prevProfile.getFilePath(), prevProfile.getFileName());
            if(del.exists()) {
                del.delete();
            }
            fileRepository.deleteById(prevProfile.getId());
        }
        member.setProfile(null);
        return new MemberDTO(member);
    }

    @Transactional
    public int uploadProfile(Member member, MultipartFile profile) {
        File path = new File(PROFILE_PATH);
        if(!path.exists()) {
            path.mkdir();
        }
        String originName = profile.getOriginalFilename();
        String contentType = profile.getContentType();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + originName.substring(originName.lastIndexOf('.'));

        FileEntity file = FileEntity.builder()
                .member(member)
                .study(null)
                .article(null)
                .meeting(null)
                .filePath(PROFILE_PATH)
                .fileName(saveFileName)
                .fileOriginName(originName)
                .contentType(contentType)
                .build();

        file = fileRepository.save(file);

        try {
            profile.transferTo(new File(PROFILE_PATH, saveFileName));
        } catch (IOException e) {
            return 1;
        }
        member.setProfile(file);

        return 2;
    }
}
