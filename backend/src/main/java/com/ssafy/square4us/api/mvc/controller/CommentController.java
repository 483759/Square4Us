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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/study/{studyId}/{articleId}/comment")
public class CommentController {

    private final ArticleService articleService;
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
                                                              @PathVariable("studyId") Long studyId,
                                                              @PathVariable("articleId") Long articleId,
                                                              @RequestBody @Parameter(name = "댓글 생성 정보", required = true) CommentDTO.CreatePostReq req) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);
        System.out.println(member);
        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        CommentDTO newComment = commentService.createComment(articleId, member.getId(), req);

        if (newComment == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(CommentDTO.CreatePostRes.of(201, "게시글 생성", newComment.getId()));
    }
}
