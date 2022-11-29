package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.util.UserDependency;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private UserService userService;
    @BeforeEach
    void init(){
        userService = UserDependency.getUserService();
    }

    @Test
    void registerUserTest(){
        String userId = "id";
        String userPw = "pw";
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest(userId,userPw);
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
    }

    @Test
    void registerUserConflictTest(){
        String userId = "id";
        String userPw = "pw";
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest(userId,userPw);
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
        UserMockFactory.makeUserRegisterRequest(userId,userPw);
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
    }

    @Test
    void deleteUserTest(){
        String userId = "id";
        String userPw = "pw";
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest(userId,userPw);
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
        userService.deleteUser(userId);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(0);
    }
}
