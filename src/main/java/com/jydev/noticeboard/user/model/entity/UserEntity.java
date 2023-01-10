package com.jydev.noticeboard.user.model.entity;

import com.jydev.noticeboard.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class UserEntity {
    private String profileImageUrl;
    private String email;
    private String nickname;
    private String id;
    private String password;
    private UserRole role;
    private LocalDateTime createdDateTime;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        UserEntity user = (UserEntity) obj;
        return Objects.equals(user.id,this.getId()) && Objects.equals(password,((UserEntity) obj).password);
    }
}
