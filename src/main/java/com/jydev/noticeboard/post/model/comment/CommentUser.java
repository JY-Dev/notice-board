package com.jydev.noticeboard.post.model.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUser {
    private String id;
    private String nickname;
    private String profileImageUrl;
}
