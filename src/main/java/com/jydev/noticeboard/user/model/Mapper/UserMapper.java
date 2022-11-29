package com.jydev.noticeboard.user.model.Mapper;


import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.UserRole;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRegisterRequest request){
        return new UserEntity(request.getEmail(),request.getNickname(),request.getId(),request.getPassword(), UserRole.COMMON);
    }
    public User toUser(UserEntity userEntity){
        return new User(userEntity.getEmail(),userEntity.getNickname(),userEntity.getId(),userEntity.getRole());
    }
}
