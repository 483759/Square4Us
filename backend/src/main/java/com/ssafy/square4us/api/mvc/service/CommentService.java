package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

public interface CommentService {
    CommentDTO createComment(Long articleId, Long MemberId, CommentDTO.CommentCreatePostReq req);
    Page<CommentDTO> findCommentsWithPaging(Pageable pageable, Long articleId);
    CommentDTO readComment(Long commentId);
    void deleteByCommentId(Long commentId);
    Boolean updateComment(Long commentId, CommentDTO.CommentCreatePostReq req);
}
