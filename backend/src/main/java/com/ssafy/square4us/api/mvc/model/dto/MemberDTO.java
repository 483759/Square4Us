package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {
    private Long id;
    private String email;
    private String password;
    private MemberRole role;
    private String nickname;
    private String profile_name;
    private String profile_path;
    private int report;

    public MemberDTO(Long id, String email, String password, MemberRole role, String nickname, String profile_name, String profile_path, int report) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.profile_name = profile_name;
        this.profile_path = profile_path;
        this.report = report;
    }

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.nickname = member.getNickname();
        this.profile_name = member.getProfile_name();
        this.profile_path = member.getProfile_path();
        this.report = member.getReport();
    }

    @Getter
    //@Schema(description = "MemberJoinPostRequest")
    public static class JoinPostReq {
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

    @Getter
    public static class InfosGetRes {
        List<MemberDTO> memberList;

        public InfosGetRes(List<MemberDTO> memberList) {
            this.memberList = memberList;
        }

        public static BasicResponseBody<MemberDTO.InfosGetRes> of(Integer statusCode, String message, List<MemberDTO> memberList) {
            return BasicResponseBody.of(statusCode, message, new InfosGetRes(memberList));
        }
    }
}
