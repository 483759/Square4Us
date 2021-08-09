package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleDTO createArticle(Long studyId, Long MemberId, ArticleDTO.CreatePostReq req);
    PageImpl<ArticleDTO> findStudiesWithPaging(Pageable pageable, Long studyId);
    ArticleDTO readArticle(Long articleId);
    void deleteByArticleId(Long articleId);
}
