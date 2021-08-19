package com.ssafy.square4us.api.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private String introduction;

    @ColumnDefault("0")
    private int report;

    @OneToOne(mappedBy = "member")
    private FileEntity profile;

    @OneToMany(mappedBy = "member")
    private List<ArticleEvaluation> evals = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String password, MemberRole role, String nickname, String introduction, int report, FileEntity profile, List<ArticleEvaluation> evals) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.introduction = introduction;
        this.report = report;
        this.profile = profile;
        this.evals = evals;
    }
}