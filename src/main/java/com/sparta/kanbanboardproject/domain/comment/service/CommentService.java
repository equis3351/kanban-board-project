package com.sparta.kanbanboardproject.domain.comment.service;

import com.sparta.kanbanboardproject.domain.comment.dto.CommentRequestDto;
import com.sparta.kanbanboardproject.domain.comment.dto.CommentResponseDto;
import com.sparta.kanbanboardproject.domain.comment.entity.Comment;
import com.sparta.kanbanboardproject.domain.comment.repository.CommentRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto addComment(User user, CommentRequestDto commentRequestDto){
        Comment comment=new Comment(commentRequestDto);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto getAllComment(){

    }
}
