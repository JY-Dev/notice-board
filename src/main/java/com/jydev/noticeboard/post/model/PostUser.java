package com.jydev.noticeboard.post.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PostUser {
    private String imageUrl;
    private String nickname;
    private String id;
}