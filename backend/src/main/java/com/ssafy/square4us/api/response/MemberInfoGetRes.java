package com.ssafy.square4us.api.response;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "MemberInfoResponse")
public class MemberInfoGetRes extends BasicResponseBody {
    @Schema(name = "회원 이메일")
    String email;
    @Schema(name = "회원 권한(Auth)")
    String role;
    @Schema(name = "회원 닉네임")
    String nickname;
    @Schema(name = "회원의 프로필 사진명")
    String profile_name;
    @Schema(name = "회원의 프로필 사진 경로")
    String profile_path;
    @Schema(name = "신고 누적 회수")
    int report;

    public static MemberInfoGetRes of(Integer statusCode, String message, Member member) {
        MemberInfoGetRes res = new MemberInfoGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setEmail(member.getEmail());
        res.setRole(member.getRole().toString());
        res.setNickname(member.getNickname());
        res.setProfile_name(member.getProfile_name());
        res.setProfile_path(member.getProfile_path());
        res.setReport(member.getReport());
        return res;
    }
}
