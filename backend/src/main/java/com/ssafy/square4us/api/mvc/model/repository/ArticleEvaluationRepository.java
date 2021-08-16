package com.ssafy.square4us.api.mvc.model.repository;

import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.ArticleEvaluation;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleEvaluationRepository extends JpaRepository<ArticleEvaluation, Long> {
    ArticleEvaluation findByMemberAndArticle(Member member, Article Article);
}
