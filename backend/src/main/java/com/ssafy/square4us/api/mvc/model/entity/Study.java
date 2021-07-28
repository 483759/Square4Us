package com.ssafy.square4us.api.mvc.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

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
public class Study extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "study_id")
	private Long id;

	private String category;

	@Column(name = "study_name")
	private String name;

	@Column(name = "is_dissolve")
	private boolean isDessolved = false;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dissolve_at")
	private Date dsvDate;

	@Builder
	public Study(String category, String name) {
		super();
		this.category = category;
		this.name = name;
	}
}
