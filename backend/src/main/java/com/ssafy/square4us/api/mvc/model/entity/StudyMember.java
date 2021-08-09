package com.ssafy.square4us.api.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "study_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class StudyMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_id")
    private Long id;

    @Column(name = "leader_flag")
    private char leader;

    @Column(name = "accepted_flag")
    private char accepted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    private Study study;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @Builder
    public StudyMember(char leader, char accepted, Study study, Member member) {
        super();
        this.leader = leader;
        this.accepted = accepted;
        this.study = study;
        this.member = member;
    }
}
