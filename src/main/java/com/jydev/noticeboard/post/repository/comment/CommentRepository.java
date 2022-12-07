package com.jydev.noticeboard.post.repository.comment;

import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;

import java.util.List;

public interface CommentRepository {
    List<CommentEntity> findCommentsByPostId(Long postId);
    CommentEntity saveComment(CommentRequest commentRequest);
    void deleteCommentById(Long commentId);
}
