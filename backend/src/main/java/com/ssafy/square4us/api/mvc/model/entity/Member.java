package com.ssafy.square4us.api.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    MemberRole role = MemberRole.USER;

    private String nickname;

    @Column(nullable = true)
    private String profile_name;

    @Column(nullable = true)
    private String profile_path;

    @ColumnDefault("0")
    private int report;

    @Builder
    public Member(String email, String password, String nickname, String profile_name, String profile_path) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile_name = profile_name;
        this.profile_path = profile_path;
    }

    @Getter
    @Schema(description = "MemberJoinPostRequest")
    public static class JoinPostReq{
        @Schema(name = "email", example = "ssafy@naver.com")
        String email;
        @Schema(name = "password", example = "password1234")
        String password;
        @Schema(name = "nickname", example = "윤이진")
        String nickname;

        @Builder
        public JoinPostReq(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }
    }
}
