package com.ssafy.square4us.api.mvc.service;


import com.ssafy.square4us.api.mvc.model.dto.CommentDTO;
import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.Comment;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepo;
    private final CommentRepositorySupport commentRepositorySupport;
    private final CommentRepository commentRepo;
    private final MemberRepository memberRepo;

    @Override
    @Transactional
    public CommentDTO createComment(Long articleId, Long memberId, CommentDTO.CommentCreatePostReq req) {
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

    @Override
    @Transactional
    public Page<CommentDTO> findCommentsWithPaging(Pageable pageable, Long articleId) {
        return commentRepositorySupport.findArticlesWithPaging(pageable, articleId);
    }

    @Override
    public CommentDTO readComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).get();
        if(comment == null) {
            return null;
        }
        return new CommentDTO(comment);
    }

    @Override
    @Transactional
    public void deleteByCommentId(Long commentId) {
        commentRepo.deleteById(commentId);
    }

    @Override
    @Transactional
    public Boolean updateComment(Long commentId, CommentDTO.CommentCreatePostReq req) {
        Optional<Comment> comment = commentRepo.findById(commentId);
        if(!comment.isPresent()) {
            return false;
        }
        Long result = commentRepositorySupport.updateComment(commentId, req.getContent());
        return result != 0;
    }
}
