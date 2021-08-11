package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleDTO {
    private Long id;
    private String category;
    private String title;
    private String content;
    private StudyDTO study;
    private MemberDTO member;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer hit;
    private Integer good;
    private Integer dislike;
    private List<FileDTO> files;

    @Builder
    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.category = article.getCategory();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.study = new StudyDTO(article.getStudy());
        this.member = new MemberDTO(article.getMember());
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
        this.hit = article.getHit();
        this.good = article.getGood();
        this.dislike = article.getDislike();
        if(article.getFiles() != null) {
            this.files = new ArrayList<>();
            for(FileEntity fe: article.getFiles()) {
                this.files.add(new FileDTO(fe));
            }
        } else {
            this.files = null;
        }
    }

    @Getter
    @Schema(description = "Article Create Post Request")
    public static class CreatePostReq {
        @Schema(name = "category", example = "15")
        String category;

        @Schema(name = "title", example = "제목")
        String title;

        @Schema(name = "content", example = "내용")
        String content;

        @Builder
        public CreatePostReq(String category, String title, String content) {
            this.category = category;
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    public static class CreatePostRes {
        Long id;

        public CreatePostRes(Long id) {
            this.id = id;
        }

        public static BasicResponseBody<ArticleDTO.CreatePostRes> of(Integer statusCode, String message, Long id) {
            return BasicResponseBody.of(statusCode, message, new ArticleDTO.CreatePostRes(id));
        }
    }

    @Getter
    public static class ListGetRes {
        Page<ArticleDTO> articleList;

        public ListGetRes(Page<ArticleDTO> articleList) { this.articleList = articleList; }

        public static BasicResponseBody<ArticleDTO.ListGetRes> of(Integer statusCode, String message, Page<ArticleDTO> articleList) {
            return BasicResponseBody.of(statusCode, message, new ArticleDTO.ListGetRes(articleList));
        }
    }

    @Getter
    @Schema(description = "특정 게시물 정보 조회 Response")
    public static class ArticleGetRes {
        @Schema(description = "게시물 id", example = "1")
        private Long id;

        @Schema(description = "게시물 작성자 email", example = "ssafy@naver.com")
        private String email;

        @Schema(description = "카테고리", example = "free")
        private String category;

        @Schema(description = "제목", example = "title")
        private String title;

        @Schema(description = "내용", example = "내용")
        private String content;

        @Schema(description = "작성 날짜", example = "2021-08-08 12:24:41")
        private LocalDateTime createdDate;

        @Schema(description = "수정 날짜", example = "2021-08-08 12:24:42")
        private LocalDateTime modifiedDate;

        @Schema(description = "조회수", example = "3")
        private Integer hit;

        @Schema(description = "좋아요", example = "3")
        private Integer good;

        @Schema(description = "싫어요", example = "3")
        private Integer dislike;

        @Schema(description = "첨부파일")
        private List<FileDTO> files;

        public ArticleGetRes(Long id, String email, String category, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, Integer hit, Integer good, Integer dislike, List<FileDTO> files) {
            this.id = id;
            this.email = email;
            this.category = category;
            this.title = title;
            this.content = content;
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
            this.hit = hit;
            this.good = good;
            this.dislike = dislike;
            this.files = files;
        }

        public static BasicResponseBody<ArticleDTO.ArticleGetRes> of(Integer statusCode, String message, ArticleDTO article) {
            ArticleDTO.ArticleGetRes result = new ArticleDTO.ArticleGetRes(article.getId(), article.getMember().getEmail(), article.getCategory(),
                                                                           article.getTitle(), article.getContent(), article.getCreatedDate(),
                                                                           article.getModifiedDate(), article.getHit(), article.getGood(), article.getDislike(), article.getFiles());
            return BasicResponseBody.of(statusCode, message, result);
        }
    }
}
