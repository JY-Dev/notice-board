package com.jydev.noticeboard.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@SpringBootTest
public class HttpResponseMessageResolverTest {
    @Autowired
    HttpResponseMessageResolver httpResponseMessageResolver;
    @Autowired
    MessageSource messageSource;

    @Test
    void getHttpMessageTest(){
        String resolve = httpResponseMessageResolver.resolve(HttpStatus.OK);
        String message = messageSource.getMessage("http.ok", null, Locale.getDefault());
        Assertions.assertThat(message).isEqualTo(resolve);
    }

    @Test
    void getHttpDetailMessageTest(){
        String resolve = httpResponseMessageResolver.resolve(HttpStatus.OK,"post");
        String message = messageSource.getMessage("http.ok.post", null, Locale.getDefault());
        Assertions.assertThat(message).isEqualTo(resolve);
    }
}
