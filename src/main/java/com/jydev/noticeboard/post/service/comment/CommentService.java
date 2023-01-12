package com.jydev.noticeboard.post.service.comment;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> registerComment(CommentRequest commentRequest);
    void deleteComment(Long commentId);

    Optional<Comment> getComment(Long commentId);
}
