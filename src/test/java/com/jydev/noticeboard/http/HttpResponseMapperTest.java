package com.jydev.noticeboard.http;

import com.jydev.noticeboard.http.model.HttpResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class HttpResponseMapperTest {
    @Autowired
    HttpResponseMapper httpResponseMapper;
    @Autowired
    HttpResponseMessageResolver httpResponseMessageResolver;

    @Test
    public void dataToHttpResponseTest(){
        String data = "data";
        int code = HttpStatus.OK.value();
        String message = httpResponseMessageResolver.resolve(HttpStatus.OK);
        HttpResponse<String> target = new HttpResponse<>(code, message, data);
        HttpResponse<String> result = httpResponseMapper.toHttpResponse(HttpStatus.OK, data);
        Assertions.assertThat(target).isEqualTo(result);
    }
}
