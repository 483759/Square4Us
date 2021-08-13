package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Member getMemberByEmail(String email);

    MemberDTO getMemberDTOByEmail(String email);

    List<MemberDTO> getMembersByStudy(Long studyId);

    List<MemberDTO> getMembersWaitJoin(Long studyId);

    MemberDTO createMember(MemberDTO.JoinPostReq joinInfo);

    Long updateMemberByEmail(Long memberId, MemberDTO.UpdatePatchReq updateInfo);

    MemberDTO updateProfileByEmail(String email, MultipartFile profile) throws IOException;

    MemberDTO deleteProfileByEmail(String email);

    Boolean deleteMemberByEmail(String email);
}
