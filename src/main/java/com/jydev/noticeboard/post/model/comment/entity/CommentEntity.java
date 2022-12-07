package com.jydev.noticeboard.post.model.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentEntity {
    Long postId;
    Long id;
    Long parentId;
    String content;
    LocalDateTime createDateTime;
}
