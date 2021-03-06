package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleService {
    ArticleDTO createArticle(Long studyId, Long MemberId, ArticleDTO.WritePostReq req);
    PageImpl<ArticleDTO> findStudiesWithPaging(Pageable pageable, Long studyId);
    ArticleDTO readArticle(Long articleId);
    ArticleDTO getArticle(Long articleId);
    void deleteByArticleId(Long articleId);
    ArticleDTO evalArticle(String email, Long articleId, String what) throws Exception;

    void updateArticle(Long articleId, ArticleDTO.WritePostReq req, MultipartFile[] files) throws IOException;
    void uploadFiles(Long articleId, MultipartFile[] files) throws IOException;
    PageImpl<ArticleDTO> getArticleListWithSearchingAndPaging(Pageable pageable, Long studyId, String key, String word);
}
