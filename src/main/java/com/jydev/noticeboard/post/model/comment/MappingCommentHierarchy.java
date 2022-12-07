package com.jydev.noticeboard.post.model.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MappingCommentHierarchy {
    private Comment parentComment;
    private List<Comment> childComment;
}
