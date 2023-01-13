package com.jydev.noticeboard.user.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private String profileImageUrl;
    private String email;
    private String nickname;
    private String id;
    private UserRole role;
}
