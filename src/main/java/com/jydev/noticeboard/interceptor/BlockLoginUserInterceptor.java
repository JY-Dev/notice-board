package com.jydev.noticeboard.interceptor;

import com.jydev.noticeboard.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class BlockLoginUserInterceptor implements HandlerInterceptor {
    private final UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session != null && userService.getLoginUser(session.getId()).isPresent()){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
