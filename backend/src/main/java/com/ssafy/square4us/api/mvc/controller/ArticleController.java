package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
import com.ssafy.square4us.api.mvc.model.dto.ResponseFactory;
import com.ssafy.square4us.api.mvc.model.dto.StudyDTO;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.ArticleService;
import com.ssafy.square4us.api.mvc.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.ssafy.square4us.common.auth.MemberDetails;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/study/{studyId}/article")
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
//
    public Member authentication(Authentication auth){
        if(auth==null) return null;
        MemberDetails memberDetails = (MemberDetails) auth.getDetails();
        String memberId = memberDetails.getUsername();

        return memberService.getMemberByEmail(memberId);
    }

    @PostMapping("")
    @Operation(summary = "게시글 생성", description = "게시글 생성한다", responses = {
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "게시글 생성 실패")})
    public ResponseEntity<? extends BasicResponseBody> create(@Parameter(hidden = true) Authentication authentication,
                                                              @PathVariable("studyId") Long studyId,
                                                              @RequestBody @Parameter(name = "게시글 생성 정보", required = true) ArticleDTO.CreatePostReq req) {
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

        ArticleDTO newArticle = articleService.createArticle(studyId, member.getId(), req);

        if (newArticle == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(ArticleDTO.CreatePostRes.of(201, "게시글 생성", newArticle.getId()));
    }

    @GetMapping("")
    @Operation(summary = "게시물 목록 조회", description = "현재 스터디에서 모든 게시물의 목록을 조회한다", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> readAllWithPaging(@PathVariable("studyId") Long studyId, @Parameter int page, @Parameter int size, @Parameter(required = false) Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ArticleDTO> list = articleService.findStudiesWithPaging(pageable, studyId);
        if (list == null || list.getSize() == 0) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(ArticleDTO.ListGetRes.of(200, "조회 성공", list));
    }

    @GetMapping("{articleId}")
    @Operation(summary = "게시물 정보", description = "articleId에 해당하는 게시물 정보를 반환한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "게시물이 존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> readArticle(@Parameter(hidden = true) Authentication authentication,
                                                                   @PathVariable("studyId") Long studyId,
                                                                   @PathVariable("articleId") Long articleId) {
        if(authentication == null) {
            return ResponseFactory.forbidden();
        }

        Member member = authentication(authentication);
        if(member == null) {
            return ResponseFactory.unauthorized();
        }

        ArticleDTO article = articleService.readArticle(articleId);

        if(article == null) {
            return ResponseFactory.forbidden();
        }

        return ResponseEntity.ok(ArticleDTO.ArticleGetRes.of(200, "성공", article));
    }

    @DeleteMapping("{articleId}")
    @Operation(summary = "게시물 삭제", description = "articleId에 해당하는 게시물을 삭제한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "게시물이 존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> deleteArticle(@Parameter(hidden = true) Authentication authentication,
                                                                     @PathVariable("studyId") Long studyId,
                                                                     @PathVariable("articleId") Long articleId) {
        if(authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if(memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        ArticleDTO article = articleService.readArticle(articleId);

        if(article == null) {
            return ResponseFactory.notFound();
        }

        if(!article.getMember().getEmail().equals(email)) {
            return ResponseFactory.conflict();
        }

        articleService.deleteByArticleId(articleId);

        return ResponseFactory.ok();
    }

    @PatchMapping("{articleId}/{what}")
    @Operation(summary = "게시물 평가", description = "articleId에 해당하는 게시물을 평가한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "게시물이 존재하지 않음"),
            @ApiResponse(responseCode = "503", description = "없는 처리 방식")})
    public ResponseEntity<? extends BasicResponseBody> evalArticle(@Parameter(hidden = true) Authentication authentication,
                                                                   @PathVariable("studyId") Long studyId,
                                                                   @PathVariable("articleId") Long articleId,
                                                                   @PathVariable("what") String what) {
        if(authentication == null) {
            return ResponseFactory.unauthorized();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if(memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        if(!what.equals("like") && !what.equals("dislike")) {
            return ResponseFactory.serviceUnavailable();
        }
        articleService.evalArticle(articleId, what);

        return ResponseFactory.ok();
    }


    @PutMapping("{articleId}")
    @Operation(summary = "게시물 수정", description = "articleId에 해당하는 게시물을 수정한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "403", description = "게시물이 존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> updateArticle(@Parameter(hidden = true) Authentication authentication,
                                                                     @PathVariable("studyId") Long studyId,
                                                                     @PathVariable("articleId") Long articleId,
                                                                     @RequestBody @Parameter(name = "게시글 수정 정보", required = true) ArticleDTO.CreatePostReq req) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();
        String memberId = memberDetails.getUsername();

        Member member = memberService.getMemberByEmail(memberId);
        if (member == null) {
            return ResponseFactory.unauthorized();
        }

        ArticleDTO article = articleService.getArticle(articleId);
        if(article == null) {
            return ResponseFactory.forbidden();
        }

        if(!article.getMember().getEmail().equals(memberId)) {
            return ResponseFactory.conflict();
        }

        articleService.updateArticle(articleId, req);

        return ResponseFactory.ok();
    }
}
