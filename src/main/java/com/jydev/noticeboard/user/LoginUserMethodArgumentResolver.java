package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean hasUserType = User.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if(session == null)
            return null;
        else{
            User user = userService.getLoginUserById(session.getId()).orElse(null);
            if(user == null)
                return null;
            webRequest.setAttribute("user",user,0);
            return user;
        }
    }
}
