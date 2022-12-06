package com.jydev.noticeboard.post.model;

import com.jydev.noticeboard.user.model.PostUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {
    private Long id;
    private PostUser user;
    private String title;
    private String content;
    private LocalDateTime registerDateTime;
}
