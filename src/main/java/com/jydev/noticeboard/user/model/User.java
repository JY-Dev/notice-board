package com.jydev.noticeboard.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String email;
    private String nickname;
    private String id;
    private UserRole role;
}
