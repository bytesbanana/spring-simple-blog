package com.bytebanana.simpleblog.service;

import com.bytebanana.simpleblog.dto.CommentRequest;
import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.entity.Comment;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.exception.CommentNotFoundException;
import com.bytebanana.simpleblog.exception.PostNotFoundException;
import com.bytebanana.simpleblog.mapper.CommentMapper;
import com.bytebanana.simpleblog.repository.CommentRepository;
import com.bytebanana.simpleblog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    public void createComment(CommentRequest commentRequest) {
        Comment comment = commentMapper.mapRequestToComment(commentRequest);
        comment.setCommentId(0L);
        comment.setCreateDate(Instant.now());
        commentRepository.save(comment);
    }

    public List<CommentResponse> findAllCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found id:" + postId));
        List<Comment> comments = commentRepository.findAllByPostOrderByCreateDateAsc(post);
        List<CommentResponse> commentResponses = comments.stream()
                .map(commentMapper::mapToRespone)
                .collect(Collectors.toList());

        return commentResponses;
    }

    public void updateComment(Long commentId, CommentRequest commentRequest) {
        Comment existingCommnet = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found id:" + commentId));

        Comment comment = commentMapper.mapRequestToComment(commentRequest);
        comment.setCommentId(commentId);
        comment.setCreateDate(existingCommnet.getCreateDate());
        comment.setLastUpdateDate(Instant.now());

        commentRepository.save(comment);
    }


    public void addComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(("Post not found id:" + postId)));
        Comment comment = commentMapper.mapRequestToComment(request);
        comment.setCommentId(0l);
        comment.setCreateDate(Instant.now());
        comment.setMessage(request.getMessage());
        comment.setPost(post);

        commentRepository.save(comment);
    }

    public CommentResponse findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found id :" + commentId));
        CommentResponse commentResponse = commentMapper.mapToRespone(comment);

        return commentResponse;
    }

    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CommentNotFoundException("Comment not found id:"+commentId));
        commentRepository.delete(comment);
    }
}
