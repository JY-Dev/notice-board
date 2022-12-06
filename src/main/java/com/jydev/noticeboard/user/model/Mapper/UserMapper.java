package com.jydev.noticeboard.user.model.Mapper;


import com.jydev.noticeboard.file.FileSystem;
import com.jydev.noticeboard.file.model.FileType;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.UserRole;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final FileSystem fileSystem;
    @Value("${default.file.image}")
    private String defaultImageFileName;
    public UserEntity toEntity(UserRegisterRequest request){
        return new UserEntity(fileSystem.getHttpProtocolUrl(defaultImageFileName, FileType.IMAGE), request.getEmail(),request.getNickname(),request.getId(),request.getPassword(), UserRole.COMMON);
    }
    public User toUser(UserEntity userEntity){
        return new User(userEntity.getImageUrl(),userEntity.getEmail(),userEntity.getNickname(),userEntity.getId(),userEntity.getRole());
    }
}
