package com.ssafy.square4us.api.mvc.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Study extends BaseTimeEntity {
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
