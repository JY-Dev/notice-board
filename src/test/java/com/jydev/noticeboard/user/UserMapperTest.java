package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserDependency;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserMapperTest {
    private final UserMapper userMapper = UserDependency.userMapper;
    @Test
    void userRegisterRequestToEntityTest(){

        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        UserEntity userEntity = UserMockFactory.makeUserEntity();
        UserEntity result = userMapper.toEntity(request);
        Assertions.assertThat(userEntity).isEqualTo(result);
    }

    @Test
    void userEntityToUserTest(){
        UserEntity userEntity = UserMockFactory.makeUserEntity();
        User result = userMapper.toUser(userEntity);
        User user = UserMockFactory.makeUser();
        Assertions.assertThat(result).isEqualTo(user);
    }
}
