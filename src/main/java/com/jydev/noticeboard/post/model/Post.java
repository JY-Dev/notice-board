package com.jydev.noticeboard.post.model;

import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Post {
    private Long id;
    private PostUser user;
    private String title;
    private String content;
    private LocalDateTime registerDateTime;
    private Integer commentSize;
    private List<MappingCommentHierarchy> comments;
}
