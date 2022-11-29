package com.jydev.noticeboard;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.UserRole;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserMapperTest {
    UserMapper userMapper = new UserMapper();

    @Test
    void userRegisterRequestToEntityTest(){
        UserRegisterRequest request = new UserRegisterRequest();
        String id = "id";
        String email = "email";
        String pw = "password";
        String nickname = "nickname";
        request.setId(id);
        request.setEmail(email);
        request.setPassword(pw);
        request.setConfirmPassword(pw);
        request.setNickname(nickname);
        UserEntity userEntity = new UserEntity(email,nickname,id,pw, UserRole.COMMON);
        UserEntity result = userMapper.toEntity(request);
        Assertions.assertThat(userEntity).isEqualTo(result);
    }
}
