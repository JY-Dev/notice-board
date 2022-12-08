package com.jydev.noticeboard.post.model;

import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Post {
    private Long id;
    private PostUser user;
    private String title;
    private String content;
    private LocalDateTime registerDateTime;
    private List<MappingCommentHierarchy> comments;
}
