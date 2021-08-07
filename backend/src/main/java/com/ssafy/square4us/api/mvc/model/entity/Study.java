package com.ssafy.square4us.api.mvc.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Study extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    private String category;

    @Column(name = "study_name")
    private String name;

    @Column(name = "dismantle_flag")
    private char dismantleFlag = 'F';

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dismantle_date")
    private Date dismantleDate;

    @Builder
    public Study(Long id, String category, String name, char dismantleFlag, Date dismantleDate) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.dismantleFlag = dismantleFlag;
        this.dismantleDate = dismantleDate;
    }

}
