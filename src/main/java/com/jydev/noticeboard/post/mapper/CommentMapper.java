package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.CommentUser;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.ChildCommentEntity;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CommentMapper {
    public Comment toComment(CommentEntity commentEntity) {
        if(commentEntity == null)
            return null;
        return new Comment(commentEntity.getId(),
                commentEntity.getContent(), commentEntity.getCreatedDateTime(), commentEntity.getChild().stream().map(this::toComment).toList(), toCommentUser(commentEntity.getUserEntity()),commentEntity.getPost().getId());
    }

    public Comment toComment(ChildCommentEntity commentEntity) {
        if(commentEntity == null)
            return null;
        return new Comment(commentEntity.getId(),
                commentEntity.getContent(), commentEntity.getCreatedDateTime(), Collections.emptyList(), toCommentUser(commentEntity.getUserEntity()),commentEntity.getPost().getId());
    }

    public CommentUser toCommentUser(UserEntity userEntity) {
        if(userEntity == null)
            return null;
        return new CommentUser(userEntity.getId(), userEntity.getNickname(), userEntity.getProfileImageUrl());
    }
}
