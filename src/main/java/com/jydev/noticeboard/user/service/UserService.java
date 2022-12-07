package com.jydev.noticeboard.user.service;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String sessionId,String userId, String userPassword);
    void logout(String sessionId);
    Optional<User> getLoginUser(String sessionId);
    List<User> getLoginUsers();
    Optional<User> registerUser(UserRegisterRequest request);
    List<User> getRegisterUsers();
    void deleteUser(String userId);
}
