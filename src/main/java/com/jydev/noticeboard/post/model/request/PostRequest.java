package com.jydev.noticeboard.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequest {
    @NotBlank
    String title;
    String content;
    String userId;
}
