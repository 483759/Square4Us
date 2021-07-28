package com.ssafy.square4us.api.response;

import javax.persistence.Column;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.square4us.api.mvc.model.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "MemberInfoResponse")
public class MemberInfoGetRes {
	@Schema(name = "회원 이메일")
	String email;
	@Schema(name = "회원 권한(Auth)")
	String role;
	@Schema(name = "탈퇴한 회원인지 표시")
	boolean is_quit = false;
	@Schema(name = "회원 닉네임")
	String nickname;
	@Schema(name = "회원이 탈퇴한 날짜")
	String quit_at;
	@Schema(name = "회원의 프로필 사진명")
	String profile_name;
	@Schema(name = "회원의 프로필 사진 경로")
	String profile_path;
	@Schema(name = "신고 누적 회수")
	int report;
	
	public static MemberInfoGetRes of(Member member) {
		MemberInfoGetRes res = new MemberInfoGetRes();
		res.setEmail(member.getEmail());
		res.setRole(member.getRole().toString());
		res.set_quit(member.is_quit());
		res.setNickname(member.getNickname());
		res.setQuit_at(member.getQuit_at());
		res.setProfile_name(member.getProfile_name());
		res.setProfile_path(member.getProfile_path());
		res.setReport(member.getReport());
		return res;
	}
}
