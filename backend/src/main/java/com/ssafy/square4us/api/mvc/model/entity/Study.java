package com.ssafy.square4us.api.mvc.model.entity;

import com.ssafy.square4us.api.response.BasicResponseBody;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private char dismantle_flag = 'F';

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dismantle_date")
    private Date dismantle_date;

    @Builder
    public Study(Long id, String category, String name, char dismantle_flag, Date dismantle_date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.dismantle_flag = dismantle_flag;
        this.dismantle_date = dismantle_date;
    }

    @Getter
    public static class InfoGetRes extends BasicResponseBody {
        Study study;

        public InfoGetRes(Integer statusCode, String message, Study study) {
            super(statusCode, message);
            this.study = study;
        }

        public static InfoGetRes of(Integer statusCode, String message, Study study){
            return new InfoGetRes(statusCode, message, study);
        }
    }

}
