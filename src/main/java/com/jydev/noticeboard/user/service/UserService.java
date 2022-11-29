package com.jydev.noticeboard.user.service;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;

import java.util.List;

public interface UserService {
    User login(String sessionId,String userId, String userPassword);
    void logout(String sessionId);

    List<User> getLoginUsers();
    User registerUser(UserRegisterRequest request);
    void deleteUser(String userId);
}
