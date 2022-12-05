package com.jydev.noticeboard.post.model.entity;

import com.jydev.noticeboard.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostEntity {
    private Long id;
    private String title;
    private String content;
    private User registerUser;
}
