package com.ssafy.square4us.api.request;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "MemberUpdatePatchReq")
public class MemberUpdatePatchReq {
    @Schema(description = "회원 이메일", example = "ssafy@naver.com")
    String email;
    @Schema(description = "회원 닉네임", example = "nickname")
    String nickname;
    @Schema(description = "회원의 프로필 사진명")
    String profile_name;
    @Schema(description = "회원의 프로필 사진 경로")
    String profile_path;

    public static MemberUpdatePatchReq of(Member member) {
        MemberUpdatePatchReq req = new MemberUpdatePatchReq();
        req.setEmail(member.getEmail());
        req.setNickname(member.getNickname());
        req.setProfile_name(member.getProfile_name());
        req.setProfile_path(member.getProfile_path());
        return req;
    }

    @Override
    public String toString() {
        return "MemberUpdatePatchReq{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profile_name='" + profile_name + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}
