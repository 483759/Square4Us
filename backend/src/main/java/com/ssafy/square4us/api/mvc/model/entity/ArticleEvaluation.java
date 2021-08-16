package com.ssafy.square4us.api.mvc.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ArticleEvaluation extends BaseTimeEntity {

    @Id
    @Column(name = "article_evaluation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "evaluation")
    private Character evaluation;

    @Builder
    public ArticleEvaluation(Member member, Article article, Character evaluation) {
        this.member = member;
        this.article = article;
        this.evaluation = evaluation;
    }
}
