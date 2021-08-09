package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.CommentDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDTO createComment(Long articleId, Long MemberId, CommentDTO.CreatePostReq req);
}
