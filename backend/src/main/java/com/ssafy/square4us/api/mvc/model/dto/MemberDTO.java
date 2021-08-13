package com.ssafy.square4us.api.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private MemberRole role;
    private String nickname;
    private String introduction;
    private FileDTO profile;
    private int report;
    @JsonIgnore
    private String password;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.nickname = member.getNickname();
        this.introduction = member.getIntroduction();
        if (member.getProfile() == null) {
            this.profile = null;
        } else {
            this.profile = new FileDTO(member.getProfile());
        }
        this.report = member.getReport();
    }

    @Getter
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
    public static class AcceptPostReq {
        @Schema(name = "id")
        Long id;

        @Builder
        public AcceptPostReq(Long id) {
            this.id = id;
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
        @Schema(example = "김싸피")
        private String nickname;
        @Schema(example = "소개입니다")
        private String introduction;

        public UpdatePatchReq() {
        }

        public UpdatePatchReq(String nickname, String introduction) {
            this.nickname = nickname;
            this.introduction = introduction;
        }
    }

    @Getter
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
        @Schema(name = "회원 아이디")
        Long id;
        @Schema(name = "회원 이메일")
        String email;
        @Schema(name = "회원 권한(Auth)")
        MemberRole role;
        @Schema(name = "회원 닉네임")
        String nickname;
        @Schema(name = "회원 소개")
        private String introduction;
        @Schema(name = "회원의 프로필 사진")
        FileDTO profile;
        @Schema(name = "신고 누적 회수")
        int report;

        public InfoGetRes(Long id, String email, MemberRole role, String nickname, String introduction, FileDTO profile, int report) {
            this.id = id;
            this.email = email;
            this.role = role;
            this.nickname = nickname;
            this.introduction = introduction;
            this.profile = profile;
            this.report = report;
        }

        public static BasicResponseBody<InfoGetRes> of(Integer statusCode, String message, Long id, String email, MemberRole role, String nickname, String introduction, FileDTO profile, int report) {
            return BasicResponseBody.of(statusCode, message, new InfoGetRes(id, email, role, nickname, introduction, profile, report));
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
