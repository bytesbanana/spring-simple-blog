package com.bytebanana.simpleblog.service;

import com.bytebanana.simpleblog.dto.CommentRequest;
import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.entity.Comment;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.mapper.CommentMapper;
import com.bytebanana.simpleblog.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public void createComment(CommentRequest commentRequest) {
        Comment comment = commentMapper.mapRequestToComment(commentRequest);
        comment.setCommentId(0L);
        comment.setCreateDate(Instant.now());
        commentRepository.save(comment);
    }

    public List<CommentResponse> findAllByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPost(postId);
        List<CommentResponse> commentResponses = comments.stream().map(comment -> {
            return commentMapper.mapToRespone(comment);
        }).collect(Collectors.toList());

        return commentResponses;
    }

    public void updateComment(Long commentId,CommentRequest commentRequest)
    {
        Comment comment  = commentMapper.mapRequestToComment(commentRequest);
        comment.setCommentId(commentId);
        comment.setLastUpdateDate(Instant.now());

        commentRepository.save(comment);
    }


}
