package com.jydev.noticeboard.post.model.comment.response;

import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;

import java.util.List;

public class CommentResponse {
    Long postId;
    List<MappingCommentHierarchy> comments;
}
