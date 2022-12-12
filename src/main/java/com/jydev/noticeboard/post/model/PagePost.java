package com.jydev.noticeboard.post.model;

import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class PagePost {
    private Long id;
    private PostUser user;
    private String title;
    private String content;
    private LocalDateTime registerDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagePost pagePost = (PagePost) o;
        return Objects.equals(id, pagePost.id) && Objects.equals(user, pagePost.user) && Objects.equals(title, pagePost.title) && Objects.equals(content, pagePost.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, title, content);
    }
}
