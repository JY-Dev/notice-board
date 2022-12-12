package com.jydev.noticeboard.post.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PostSearchRequest {
    private String keyword;
    private int pageNum;
    private int pageSize;
}
