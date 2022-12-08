package com.jydev.noticeboard.post.model.comment.entity;

import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentEntity {
    UserEntity userEntity;
    Long postId;
    Long id;
    Long parentId;
    String content;
    LocalDateTime createDateTime;
}
