package com.jydev.noticeboard.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {
    private Long id;
    private String userNickname;
    private String userId;
    private String title;
    private String content;
    private LocalDateTime registerDateTime;
}
