package com.jydev.noticeboard.post.model.comment.response;

import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long postId;
    private List<MappingCommentHierarchy> comments;
}
