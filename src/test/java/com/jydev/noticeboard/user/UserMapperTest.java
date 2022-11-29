package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserMapperTest {
    UserMapper userMapper = new UserMapper();

    @Test
    void userRegisterRequestToEntityTest(){
        String id = "id";
        String pw = "pw";
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest(id,pw);
        UserEntity userEntity = UserMockFactory.makeUserEntity(id,pw);
        UserEntity result = userMapper.toEntity(request);
        Assertions.assertThat(userEntity).isEqualTo(result);
    }
}
