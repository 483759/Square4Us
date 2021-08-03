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

	@Enumerated(EnumType.STRING)
	MemberRole role = MemberRole.USER;	

	private boolean is_quit = false;

	private String nickname;

	@Column(nullable = true)
	private String quit_at;
	@Column(nullable = true)
	private String profile_name;
	@Column(nullable = true)
	private String profile_path;
	@Column(nullable = true)
	@ColumnDefault("0")
	private int report;

	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Builder
	public Member(String email, MemberRole role, String nickname, String password) {
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
		//this.role = MemberRole.USER;
		this.nickname = nickname;
		this.password = password;
	}

	@Builder
	public Member(String email) {
		super();
		this.email = email;
	}

}
