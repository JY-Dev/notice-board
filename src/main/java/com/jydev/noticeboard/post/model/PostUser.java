package com.jydev.noticeboard.post.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PostUser {
    private String imageUrl;
    private String nickname;
    private String id;
}
