package com.jydev.noticeboard.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.noticeboard.http.HttpResponseMapper;
import com.jydev.noticeboard.http.model.HttpResponse;
import com.jydev.noticeboard.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final UserService userService;

    private final HttpResponseMapper httpResponseMapper;

    private final ObjectMapper objectMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session == null || userService.getLoginUser(session.getId()).isEmpty()){
            String contentType = request.getContentType();
            if(contentType != null &&contentType.equals("application/json")){
                PrintWriter writer = response.getWriter();
                HttpResponse<String> httpResponse = httpResponseMapper.toHttpResponse(HttpStatus.UNAUTHORIZED, "");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                writer.write(objectMapper.writeValueAsString(httpResponse));
            } else
                response.sendRedirect("/user/login?redirectURL="+request.getRequestURI());
            return false;
        }
        return true;
    }
}
