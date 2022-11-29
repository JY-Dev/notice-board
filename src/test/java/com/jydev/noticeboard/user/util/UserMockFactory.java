package com.jydev.noticeboard.user.util;

import com.jydev.noticeboard.user.model.UserRole;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;

public class UserMockFactory {
    private static final String email = "email";
    private static final String nickname = "nickname";
    public static UserRegisterRequest makeUserRegisterRequest(String id,String pw){
        UserRegisterRequest request = new UserRegisterRequest();
        request.setId(id);
        request.setEmail(email);
        request.setPassword(pw);
        request.setConfirmPassword(pw);
        request.setNickname(nickname);
        return request;
    }

    public static UserEntity makeUserEntity(String id, String pw){
        return new UserEntity(email,nickname,id,pw, UserRole.COMMON);
    }
}
