package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.dto.CommentDTO;
import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
import com.ssafy.square4us.api.mvc.model.dto.ResponseFactory;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.ArticleService;
import com.ssafy.square4us.api.mvc.service.CommentService;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.common.auth.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/{articleId}/comment")
public class CommentController {

    private final MemberService memberService;
    private final CommentService commentService;

    public Member authentication(Authentication auth){
        if(auth==null) return null;
        MemberDetails memberDetails = (MemberDetails) auth.getDetails();
        String memberId = memberDetails.getUsername();

        return memberService.getMemberByEmail(memberId);
    }

    @PostMapping("")
    @Operation(summary = "댓글 생성", description = "댓글 생성한다", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "댓글 생성 실패")})
    public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
                                                              @PathVariable("articleId") Long articleId,
                                                              @RequestBody @Parameter(name = "댓글 생성 정보", required = true) CommentDTO.CommentCreatePostReq req) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);

        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        CommentDTO newComment = commentService.createComment(articleId, member.getId(), req);

        if (newComment == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(CommentDTO.CommentCreatePostRes.of(201, "게시글 생성", newComment.getId()));
    }

    @GetMapping("")
    @Operation(summary = "댓글 목록 조회", description = "댓글 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음.")})
    public ResponseEntity<? extends BasicResponseBody> readAllWithPaging(@PathVariable("articleId") Long articleId, @Parameter int page, @Parameter int size, @Parameter(required = false) Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CommentDTO> list = commentService.findCommentsWithPaging(pageable, articleId);
        if(list == null || list.getSize() == 0) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(CommentDTO.CommentListGetRes.of(200, "조회 성공", list));
    }

    @DeleteMapping("{commentId}")
    @Operation(summary = "댓글 삭제", description = "commentId에 해당하는 댓글을 삭제한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "댓글이 존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> deleteComment(@Parameter(hidden = true) Authentication authentication,
                                                                     @PathVariable("articleId") Long articleId,
                                                                     @PathVariable("commentId") Long commentId) {
        if(authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if(memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        CommentDTO comment = commentService.readComment(commentId);

        if(comment == null) {
            return ResponseFactory.notFound();
        }

        if(!comment.getMember().getEmail().equals(email)) {
            return ResponseFactory.conflict();
        }

        commentService.deleteByCommentId(commentId);

        return ResponseFactory.ok();
    }

    @PatchMapping("{commentId}")
    @Operation(summary = "댓글 수정", description = "commentId에 해당하는 댓글을 수정한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "댓글이 존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> updateComment(@Parameter(hidden = true) Authentication authentication,
                                                                     @PathVariable("articleId") Long articleId,
                                                                     @PathVariable("commentId") Long commentId,
                                                                     @RequestBody @Parameter(name = "댓글 수정 정보", required = true) CommentDTO.CommentCreatePostReq req) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);
        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        CommentDTO comment = commentService.readComment(commentId);
        if(comment == null) {
            return ResponseFactory.forbidden();
        }

        if(!comment.getMember().getEmail().equals(memberId)) {
            return ResponseFactory.conflict();
        }

        commentService.updateComment(commentId, req);

        return ResponseFactory.ok();
    }
}
