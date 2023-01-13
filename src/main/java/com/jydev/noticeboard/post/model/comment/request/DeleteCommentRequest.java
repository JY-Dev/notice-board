package com.jydev.noticeboard.post.model.comment.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeleteCommentRequest {
    private long parentId;
    private long commentId;
}
