package com.ssafy.square4us.api.mvc.service;


import com.ssafy.square4us.api.mvc.model.dto.CommentDTO;
import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.Comment;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepo;
    private final CommentRepository commentRepo;
    private final MemberRepository memberRepo;

    @Override
    public CommentDTO createComment(Long articleId, Long memberId, CommentDTO.CreatePostReq req) {
        Optional<Article> article = articleRepo.findById(articleId);
        Optional<Member> member = memberRepo.findById(memberId);
        if(!article.isPresent() || !member.isPresent()) {
            return null;
        }

        Comment comment = commentRepo.save(
                Comment.builder()
                        .article(article.get())
                        .member(member.get())
                        .content(req.getContent())
                        .build()
        );

        return new CommentDTO(comment);
    }
}
