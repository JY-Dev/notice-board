package com.jydev.noticeboard.post.model.comment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CommentUser {
    private String id;
    private String nickname;
    private String profileImageUrl;
}
