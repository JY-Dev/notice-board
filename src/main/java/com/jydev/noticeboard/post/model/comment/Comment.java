package com.jydev.noticeboard.post.model.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {
    Long postId;
    Long id;
    Long parentId;
    String content;
    LocalDateTime registerDateTime;
}
