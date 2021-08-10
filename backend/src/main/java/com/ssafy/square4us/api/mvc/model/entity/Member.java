package com.ssafy.square4us.api.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    private String nickname;

    @ColumnDefault("0")
    private int report;

    @OneToOne(mappedBy = "member")
    private FileEntity profile;

    @Builder
    public Member(Long id, String email, String password, MemberRole role, String nickname, int report, FileEntity profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.report = report;
        this.profile = profile;
    }
}