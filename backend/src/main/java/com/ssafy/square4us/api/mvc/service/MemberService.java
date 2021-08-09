package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;

import java.util.List;

public interface MemberService {
    Member getMemberByEmail(String email);

    MemberDTO getMemberDTOByEmail(String email);

    List<MemberDTO> getMembersByStudy(Long studyId);

    List<MemberDTO> getMembersWaitJoin(Long studyId);

    MemberDTO createMember(MemberDTO.JoinPostReq joinInfo);

    Long updateMemberByEmail(String email, MemberDTO.UpdatePatchReq updateInfo);

    void deleteMemberByEmail(String email);
}
