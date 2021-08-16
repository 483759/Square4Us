package com.ssafy.square4us.api.mvc.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "hit")
    private Integer hit = 0;

    @Column(name = "good")
    private Integer good = 0;

    @Column(name = "dislike")
    private Integer dislike = 0;

    @OneToMany(mappedBy = "article")
    private List<FileEntity> files = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<ArticleEvaluation> evals = new ArrayList<>();

    @Builder
    public Article(Member member, Study study, String category, String title, String content, List<FileEntity> files, List<ArticleEvaluation> evals) {
        this.member = member;
        this.study = study;
        this.category = category;
        this.title = title;
        this.content = content;
        this.files = files;
        this.evals = evals;
    }
}
