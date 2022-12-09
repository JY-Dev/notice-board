package com.jydev.noticeboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.noticeboard.http.HttpResponseMapper;
import com.jydev.noticeboard.interceptor.LoginCheckInterceptor;
import com.jydev.noticeboard.interceptor.BlockLoginUserInterceptor;
import com.jydev.noticeboard.user.LoginUserMethodArgumentResolver;
import com.jydev.noticeboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UserService userService;
    private final HttpResponseMapper httpResponseMapper;
    private final ObjectMapper objectMapper;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserMethodArgumentResolver(userService));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(userService,httpResponseMapper,objectMapper))
                .order(1)
                .addPathPatterns("/post","/post/{*}/**");
        registry.addInterceptor(new BlockLoginUserInterceptor(userService))
                .order(1)
                .addPathPatterns("/user/login","/user/register");
    }
}
