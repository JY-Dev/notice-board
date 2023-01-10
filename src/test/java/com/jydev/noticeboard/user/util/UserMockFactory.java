package com.jydev.noticeboard.user.util;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.UserRole;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;

import java.time.LocalDateTime;

public class UserMockFactory {

    public static UserRegisterRequest makeUserRegisterRequest(){
        UserRegisterRequest request = new UserRegisterRequest();
        request.setId(UserData.userId);
        request.setEmail(UserData.email);
        request.setPassword(UserData.userPw);
        request.setConfirmPassword(UserData.userPw);
        request.setNickname(UserData.nickname);
        return request;
    }

    public static UserEntity makeUserEntity(){
        return new UserEntity(UserData.profileImageUrl,UserData.email,UserData.nickname,UserData.userId,UserData.userPw, UserRole.COMMON, LocalDateTime.now());
    }

    public static User makeUser(){
        return new User(UserData.profileImageUrl,UserData.email,UserData.nickname,UserData.userId,UserRole.COMMON);
    }
}
