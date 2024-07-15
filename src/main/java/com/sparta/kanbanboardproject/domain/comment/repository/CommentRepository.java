package com.sparta.kanbanboardproject.domain.comment.repository;

import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {

}
