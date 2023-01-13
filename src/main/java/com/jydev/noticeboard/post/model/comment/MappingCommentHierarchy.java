package com.jydev.noticeboard.post.model.comment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MappingCommentHierarchy {
    private Comment parentComment;
    private List<Comment> childComments;
}
