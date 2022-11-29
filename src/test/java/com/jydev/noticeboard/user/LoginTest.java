package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserDependency;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {
    private UserService userService;
    private final String userId = "id";
    private final String userPw = "pw";
    private static User registerUser;

    @BeforeEach
    void init(){
        userService = UserDependency.getUserService();
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest(userId, userPw);
        registerUser = userService.registerUser(request);
    }

    @Test
    void userLoginTest(){
        String sessionId = "sessionId";
        User loginUser = userService.login(sessionId, userId, userPw);
        Assertions.assertThat(registerUser).isEqualTo(loginUser);
    }

    /**
     * 동시 로그인 가능 횟수는 2회까지 가능하며 2회 초과가 된 경우에는 로그인 실패
     */
    @Test
    void overConcurrentUserLoginCountFailTest(){
        String sessionId = "sessionId";
        String sessionId2 = "sessionId2";
        String sessionId3 = "sessionId3";
        userService.login(sessionId, userId, userPw);
        User loginUser = userService.login(sessionId2, userId, userPw);
        Assertions.assertThat(loginUser).isEqualTo(registerUser);
        User loginFailUser = userService.login(sessionId3, userId, userPw);
        Assertions.assertThat(loginFailUser).isNull();
    }

    @Test
    void userLogoutTest(){
        String sessionId = "sessionId";
        userService.login(sessionId, userId, userPw);
        Assertions.assertThat(userService.getLoginUsers().size()).isEqualTo(1);
        userService.logout(sessionId);
        Assertions.assertThat(userService.getLoginUsers().size()).isEqualTo(0);
    }
}
