package com.ssafy.square4us.api.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
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
    private MemberRole role = MemberRole.USER;

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
    //@Schema(description = "MemberJoinPostRequest")
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

    @Getter
    public static class LoginPostReq {
        @Schema(name = "email", example = "ssafy@naver.com")
        private String email;
        @Schema(name = "password", example = "password1234")
        private String password;

        public LoginPostReq(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    public static class UpdatePatchReq {
        private String nickname;
        private String profile_name;
        private String profile_path;

        public UpdatePatchReq(String nickname, String profile_name, String profile_path) {
            this.nickname = nickname;
            this.profile_name = profile_name;
            this.profile_path = profile_path;
        }
    }

    @Getter
    //@Schema(description = "MemberLoginPostResponse")
    public static class LoginPostRes {
        @Schema(name = "JWT Authentication Token")
        private String accessToken;

        public LoginPostRes(String accessToken) {
            this.accessToken = accessToken;
        }

        public static BasicResponseBody<LoginPostRes> of(Integer statusCode, String message, String accessToken) {
            return BasicResponseBody.of(statusCode, message, new LoginPostRes(accessToken));
        }
    }

    @Getter
    public static class InfoGetRes {
        @Schema(name = "회원 이메일")
        String email;
        @Schema(name = "회원 권한(Auth)")
        MemberRole role;
        @Schema(name = "회원 닉네임")
        String nickname;
        @Schema(name = "회원의 프로필 사진명")
        String profile_name;
        @Schema(name = "회원의 프로필 사진 경로")
        String profile_path;
        @Schema(name = "신고 누적 회수")
        int report;

        public InfoGetRes(String email, MemberRole role, String nickname, String profile_name, String profile_path, int report) {
            this.email = email;
            this.role = role;
            this.nickname = nickname;
            this.profile_name = profile_name;
            this.profile_path = profile_path;
            this.report = report;
        }

        public static BasicResponseBody<InfoGetRes> of(Integer statusCode, String message, String email, MemberRole role, String nickname, String profile_name, String profile_path, int report) {
            return BasicResponseBody.of(statusCode, message, new InfoGetRes(email, role, nickname, profile_name, profile_path, report));
        }
    }
}
