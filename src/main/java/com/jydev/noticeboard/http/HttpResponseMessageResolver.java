package com.jydev.noticeboard.http;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class HttpResponseMessageResolver {
    private final MessageSource messageSource;

    public String resolve(HttpStatus httpStatus,String...detailCodes){
        String code = httpStatus.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("http").append(".").append(code);
        Arrays.stream(detailCodes).forEach(detailCode -> sb.append(".").append(detailCode));
        return messageSource.getMessage(sb.toString(), null, "Http Error", Locale.getDefault());
    }
}
