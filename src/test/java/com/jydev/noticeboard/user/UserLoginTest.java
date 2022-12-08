package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserDependency;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserLoginTest {
    private UserService userService;
    private Optional<User> registerUser;

    @BeforeEach
    void init(){
        userService = UserDependency.getUserService();
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        registerUser = userService.registerUser(request);
    }

    @Test
    void userLoginTest(){
        Optional<User> loginUser = userService.login(UserData.sessionId, UserData.userId,UserData.userPw);
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
        userService.login(sessionId, UserData.userId, UserData.userPw);
        Optional<User> loginUser = userService.login(sessionId2, UserData.userId, UserData.userPw);
        Assertions.assertThat(loginUser).isEqualTo(registerUser);
        Optional<User> loginFailUser = userService.login(sessionId3, UserData.userId, UserData.userPw);
        Assertions.assertThat(loginFailUser.isEmpty()).isTrue();
    }

    @Test
    void userLogoutTest(){
        userService.login(UserData.sessionId, UserData.userId, UserData.userPw);
        Assertions.assertThat(userService.getLoginUsers().size()).isEqualTo(1);
        userService.logout(UserData.sessionId);
        Assertions.assertThat(userService.getLoginUsers().size()).isEqualTo(0);
    }
}
