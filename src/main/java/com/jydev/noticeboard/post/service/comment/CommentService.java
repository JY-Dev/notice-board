package com.jydev.noticeboard.post.service.comment;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.comment.response.CommentResponse;

import java.util.Optional;

public interface CommentService {
    Optional<Comment> registerComment(CommentRequest commentRequest);
    void deleteComment(Long commentId);
    CommentResponse getComments(Long postId);
}
