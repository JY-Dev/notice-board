package com.jydev.noticeboard.http.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class HttpResponse<T> {
    private Integer code;
    private String message;
    private T data;
}
