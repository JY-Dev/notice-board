package com.jydev.noticeboard.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PostEditRequest {
    private Long id;
    @NotBlank
    private String title;
    private String content;
}
