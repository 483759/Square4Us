package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.entity.StudyMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMemberDTO {
    private Long id;
    private char leader;
    private char accepted;
    private Study study;
    private Member member;

    public StudyMemberDTO(Long id, char leader, char accepted, Study study, Member member) {
        this.id = id;
        this.leader = leader;
        this.accepted = accepted;
        this.study = study;
        this.member = member;
    }

    public StudyMemberDTO(StudyMember studyMember) {
        this.id = studyMember.getId();
        this.leader = studyMember.getLeader();
        this.accepted = studyMember.getAccepted();
        this.study = studyMember.getStudy();
        this.member = studyMember.getMember();
    }
}
