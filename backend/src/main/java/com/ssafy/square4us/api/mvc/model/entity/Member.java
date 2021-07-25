package com.ssafy.square4us.api.mvc.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	String email;

//	@Enumerated(EnumType.STRING)
//	MemberRole role;		
//	시간날 때 Enum으로 수정할 것
	@ColumnDefault("USER")
	String role;

	boolean is_quit = false;

	String nickname;

	@Column(nullable = true)
	String quit_at;
	@Column(nullable = true)
	String boj_id;
	@Column(nullable = true)
	String boj_rank;
	@Column(nullable = true)
	String profile_name;
	@Column(nullable = true)
	String profile_path;
	@Column(nullable = true)
	@ColumnDefault("0")
	int report;

	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String password;

	@Builder
	public Member(String email, String role, String nickname, String password) {
		super();
		this.email = email;
		this.role = role;
		this.nickname = nickname;
		this.password = password;
	}

	@Builder
	public Member(String email, String nickname, String password) {
		super();
		this.email = email;
		this.role = "USER";
		this.nickname = nickname;
		this.password = password;
	}
}
