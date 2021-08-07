package com.ssafy.square4us.api.mvc.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Meeting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "thumbnail_name")
    private String thumbnailName;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "maximum")
    private int maximum = 4;

    @Column(name = "run_flag")
    private char run_flag = 'T';

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    private Study study;

    @Builder
    public Meeting(String thumbnailName, String thumbnailPath, int maximum, char run_flag, Study study) {
        this.thumbnailName = thumbnailName;
        this.thumbnailPath = thumbnailPath;
        this.maximum = maximum;
        this.run_flag = run_flag;
        this.study = study;
    }

}
