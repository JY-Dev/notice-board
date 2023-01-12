package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.CommentUser;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CommentMapper {
    public Comment toComment(CommentEntity commentEntity) {
        CommentEntity parentComment = commentEntity.getParent();
        Long parentId = Optional.ofNullable(parentComment.getId()).orElse(-1L);
        List<CommentEntity> childComments = Optional.ofNullable(commentEntity.getChild()).orElse(Collections.emptyList());
        return new Comment(commentEntity.getId(), parentId,
                commentEntity.getContent(), commentEntity.getCreatedDateTime(), childComments.stream().map(this::toComment).toList(), toCommentUser(commentEntity.getUserEntity()));
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
