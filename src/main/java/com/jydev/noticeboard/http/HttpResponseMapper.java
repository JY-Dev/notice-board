package com.jydev.noticeboard.http;

import com.jydev.noticeboard.http.model.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpResponseMapper {
    private final HttpResponseMessageResolver httpResponseMessageResolver;

    public <T> HttpResponse<T> toHttpResponse(HttpStatus httpStatus, T data){
        int code = httpStatus.value();
        String message = httpResponseMessageResolver.resolve(httpStatus);
        return new HttpResponse<>(code,message,data);
    }


}
