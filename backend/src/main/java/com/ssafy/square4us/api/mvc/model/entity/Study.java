package com.ssafy.square4us.api.mvc.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
public class Study {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cateroty;
	
	@Column(name = "study_name")
	private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date regDate;
    
    @Column(name = "is_dissolve")
    private boolean isDessolved;
    
    @Column(name = "dissolve_at")
    private Date dsvDate;
    
}
