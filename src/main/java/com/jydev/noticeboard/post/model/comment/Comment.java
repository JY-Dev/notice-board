package com.jydev.noticeboard.post.model.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Comment {
    Long id;
    String content;
    LocalDateTime registerDateTime;
    List<Comment> childComments;
    CommentUser user;
    Long postId;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Comment other)
            return user.equals(other.user) && id.equals(other.getId()) &&
                    content.equals(other.content);
        return false;
    }
}
