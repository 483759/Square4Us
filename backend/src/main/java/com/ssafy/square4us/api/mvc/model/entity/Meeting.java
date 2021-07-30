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
public class Meeting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name="study_id")
    private Long studyId;

    @Column(name = "thumbnail_name")
    private String thumbnailName;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "max_people")
    private int maxPeople;

    @Column(name = "is_run")
    private boolean isRun = false;

    @Builder
    public Meeting(long studyId, String thumbnailName, String thumbnailPath, int maxPeople) {
        super();
        this.studyId = studyId;
        this.thumbnailName = thumbnailName;
        this.thumbnailPath = thumbnailPath;
        this.maxPeople = maxPeople;
    }
}
