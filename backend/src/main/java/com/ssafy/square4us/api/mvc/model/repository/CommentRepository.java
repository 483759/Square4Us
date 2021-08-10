package com.ssafy.square4us.api.mvc.model.repository;

import com.ssafy.square4us.api.mvc.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.article join fetch c.member")
    List<Comment> findAll();
}