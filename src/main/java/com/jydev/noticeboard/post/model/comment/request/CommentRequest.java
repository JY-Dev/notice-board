package com.jydev.noticeboard.post.model.comment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String userId;
    private Long postId;
    private Long parentId;
    private String content;
}
