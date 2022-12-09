package com.jydev.noticeboard.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PostEditRequest {
    Long id;
    @NotBlank
    String title;
    String content;
}
