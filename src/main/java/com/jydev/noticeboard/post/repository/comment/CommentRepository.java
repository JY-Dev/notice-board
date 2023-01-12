package com.jydev.noticeboard.post.repository.comment;

import com.jydev.noticeboard.post.model.comment.entity.ChildCommentEntity;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;

import java.util.List;

public interface CommentRepository {
    CommentEntity saveComment(CommentRequest commentRequest, PostEntity postEntity, UserEntity userEntity);
    ChildCommentEntity saveChildComment(CommentRequest commentRequest, CommentEntity parentComment, PostEntity postEntity, UserEntity userEntity);
    void deleteCommentById(Long commentId);

    void deleteChildCommentById(Long commentId);

    CommentEntity getCommentById(Long commentId);
    ChildCommentEntity getChildCommentById(Long commentId);
}
