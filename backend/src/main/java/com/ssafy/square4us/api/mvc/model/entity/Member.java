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

<<<<<<< HEAD
	@Enumerated(EnumType.STRING)
	MemberRole role = MemberRole.USER;		
=======
//	@Enumerated(EnumType.STRING)
//	MemberRole role;		
//	시간날 때 Enum으로 수정할 것
	@ColumnDefault("USER")
	private String role;
>>>>>>> f41eb65415196b820e192b7b54f78c4def8c96f0

	private boolean is_quit = false;

	private String nickname;

	@Column(nullable = true)
	private String quit_at;
	@Column(nullable = true)
<<<<<<< HEAD
	String profile_name;
=======
	private String boj_id;
	@Column(nullable = true)
	private String boj_rank;
	@Column(nullable = true)
	private String profile_name;
>>>>>>> f41eb65415196b820e192b7b54f78c4def8c96f0
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



}
