package com.sparta.kanbanboardproject.domain.comment.repository;

import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
