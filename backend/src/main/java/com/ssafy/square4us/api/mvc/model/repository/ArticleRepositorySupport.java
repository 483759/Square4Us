package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.QArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Repository
public class ArticleRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QArticle qArticle = QArticle.article;

    public ArticleRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Article.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ArticleDTO> findAll() {
        return jpaQueryFactory
                .select(Projections.constructor(ArticleDTO.class, qArticle))
                .from(qArticle)
                .fetch();
    }

    public PageImpl<ArticleDTO> findArticlesWithPaging(Pageable pageable, Long studyId) {
        JPAQuery query = jpaQueryFactory
                .select(Projections.constructor(ArticleDTO.class, qArticle))
                .from(qArticle)
                .where(qArticle.study.id.eq(studyId));

        Long totalCount = query.fetchCount();
        List<ArticleDTO> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }
}