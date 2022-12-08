package com.jydev.noticeboard.user.model.entity;

import com.jydev.noticeboard.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class UserEntity {
    private String profileImageUrl;
    private String email;
    private String nickname;
    private String id;
    private String password;
    private UserRole role;
}
