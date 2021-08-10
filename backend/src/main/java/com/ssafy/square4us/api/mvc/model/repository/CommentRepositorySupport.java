package com.ssafy.square4us.api.mvc.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.square4us.api.mvc.model.dto.CommentDTO;
import com.ssafy.square4us.api.mvc.model.entity.Comment;
import com.ssafy.square4us.api.mvc.model.entity.QComment;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    QComment qComment = QComment.comment;

    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<CommentDTO> findAll() {
        return jpaQueryFactory
                .select(Projections.constructor(CommentDTO.class, qComment))
                .from(qComment)
                .fetch();
    }

    public PageImpl<CommentDTO> findArticlesWithPaging(Pageable pageable, Long articleId) {
        JPAQuery query = jpaQueryFactory
                .select(Projections.constructor(CommentDTO.class, qComment))
                .from(qComment)
                .where(qComment.article.id.eq(articleId));

        Long totalCount = query.fetchCount();
        List<CommentDTO> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }
}