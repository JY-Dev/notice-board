package com.jydev.noticeboard.post.model.comment.entity;

import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentEntity {
    private UserEntity userEntity;
    private Long postId;
    private Long id;
    private Long parentId;
    private String content;
    private LocalDateTime createDateTime;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CommentEntity other)
            return userEntity.equals(other.userEntity) && other.id.equals(other.getId());
        return false;
    }
}
