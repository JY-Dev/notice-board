package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void registerUserTest(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
    }

    @Test
    void registerUserConflictTest(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class,() -> userService.registerUser(request));
    }

    @Test
    void deleteUserTest(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        userService.registerUser(request);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(1);
        userService.deleteUser(UserData.userId);
        Assertions.assertThat(userService.getRegisterUsers().size()).isEqualTo(0);
    }

    @Test
    void getLoginUserByIdTest(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        userService.registerUser(request);
        userService.login(UserData.sessionId,UserData.userId,UserData.userPw);
        Optional<User> result = userService.getLoginUser(UserData.sessionId);
        User user = UserMockFactory.makeUser();
        Assertions.assertThat(result.orElse(null)).isEqualTo(user);
    }


}
