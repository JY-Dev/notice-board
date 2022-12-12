package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserDependency;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserServiceTest {
    private UserService userService;
    @BeforeEach
    void init(){
        userService = UserDependency.getUserService();
    }

    @Test
    void registerUserTest(){
        registerUser();
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(2);
    }

    @Test
    void registerUserConflictTest(){
        registerUser();
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(2);
        UserMockFactory.makeUserRegisterRequest();
        registerUser();
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(2);
    }

    @Test
    void deleteUserTest(){
        registerUser();
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(2);
        userService.deleteUser(UserData.userId);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
    }

    @Test
    void getLoginUserByIdTest(){
        registerUser();
        userService.login(UserData.sessionId,UserData.userId,UserData.userPw);
        Optional<User> result = userService.getLoginUser(UserData.sessionId);
        User user = UserMockFactory.makeUser();
        Assertions.assertThat(result.orElse(null)).isEqualTo(user);
    }

    void registerUser(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        userService.registerUser(request);
    }
}
