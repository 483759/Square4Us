package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDTO extends BasicResponseBody {
    private Long id;
    private MemberDTO member;
    private ArticleDTO article;
    private String content;

    @Builder
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.member = new MemberDTO(comment.getMember());
        this.article = new ArticleDTO(comment.getArticle());
        this.content = comment.getContent();
    }

    @Getter
    @Schema(description = "Comment Create Post Request")
    public static class CreatePostReq {
        @Schema(name = "content", example = "내용")
        String content;

        @Builder
        public CreatePostReq(String content) {
            this.content = content;
        }
    }

    @Getter
    public static class CreatePostRes {
        Long id;

        public CreatePostRes(Long id) { this.id = id; }

        public static BasicResponseBody<CommentDTO.CreatePostRes> of(Integer statusCode, String message, Long id) {
            return BasicResponseBody.of(statusCode, message, new CommentDTO.CreatePostRes(id));
        }
    }
}
