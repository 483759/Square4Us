package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

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
    @Setter
    @NoArgsConstructor
    @Schema(description = "Comment Create Post Request")
    public static class CommentCreatePostReq {
        @Schema(name = "content", example = "댓글이요")
        String content;

        @Builder
        public CommentCreatePostReq(String content) {
            this.content = content;
        }
    }

    @Getter
    public static class CommentCreatePostRes {
        Long id;

        public CommentCreatePostRes(Long id) { this.id = id; }

        public static BasicResponseBody<CommentDTO.CommentCreatePostRes> of(Integer statusCode, String message, Long id) {
            return BasicResponseBody.of(statusCode, message, new CommentDTO.CommentCreatePostRes(id));
        }
    }

    @Getter
    public static class CommentListGetRes {
        Page<CommentDTO> commentList;

        public CommentListGetRes(Page<CommentDTO> commentList) { this.commentList = commentList; }

        public static BasicResponseBody<CommentDTO.CommentListGetRes> of(Integer statusCode, String message, Page<CommentDTO> commentList) {
            return BasicResponseBody.of(statusCode, message, new CommentDTO.CommentListGetRes((commentList)));
        }
    }
}
