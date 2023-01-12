package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.CommentUser;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CommentMapper {
    public Comment toComment(CommentEntity commentEntity) {
        Long parentId = -1L;
        return new Comment(commentEntity.getId(), parentId,
                commentEntity.getContent(), commentEntity.getCreatedDateTime(), commentEntity.getChild().stream().map(this::toComment).toList(), toCommentUser(commentEntity.getUserEntity()));
    }

    public CommentUser toCommentUser(UserEntity userEntity) {
        return new CommentUser(userEntity.getId(), userEntity.getNickname(), userEntity.getProfileImageUrl());
    }


    public MappingCommentHierarchy toMappingCommentHierarchy(List<CommentEntity> commentEntities, Comment parentComment) {
        Long parentId = parentComment.getId();
        List<Comment> childComments = commentEntities.stream().filter(commentEntity -> commentEntity.getParent().getId().equals(parentId))
                .map(this::toComment).toList();
        return new MappingCommentHierarchy(parentComment, childComments);
    }
}
