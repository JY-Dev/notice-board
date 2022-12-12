package com.jydev.noticeboard.post.model.entity;

import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class PostEntity {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime registerDateTime;
    private UserEntity registerUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity that = (PostEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(registerUser, that.registerUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registerUser);
    }
}
