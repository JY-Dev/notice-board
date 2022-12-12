package com.jydev.noticeboard.post.model.comment;

import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class Comment {
    Long id;
    Long parentId;
    String content;
    LocalDateTime registerDateTime;
    CommentUser user;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Comment other)
            return user.equals(other.user) && other.id.equals(other.getId());
        return false;
    }
}
