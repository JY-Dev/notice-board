package com.jydev.noticeboard.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Post {
    private Long id;
    private String userNickname;
    private String userId;
    private String title;
    private String content;
}
