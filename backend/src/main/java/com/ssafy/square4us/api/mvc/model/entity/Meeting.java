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

    @Column(name = "meeting_name")
    private String name;

    @Column(name = "run_flag")
    private char run_flag = 'T';

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    private Study study;

    @OneToOne(mappedBy = "meeting")
    private FileEntity thumbnail;

    @Builder
    public Meeting(String name, char run_flag, Study study, FileEntity thumbnail) {
        this.name = name;
        this.run_flag = run_flag;
        this.study = study;
        this.thumbnail = thumbnail;
    }

}
