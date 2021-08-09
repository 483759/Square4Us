package com.ssafy.square4us.api.mvc.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "content")
    private String content;

    @Builder
    public Comment(Member member, Article article, String content) {
        this.member = member;
        this.article = article;
        this.content = content;
    }
}
