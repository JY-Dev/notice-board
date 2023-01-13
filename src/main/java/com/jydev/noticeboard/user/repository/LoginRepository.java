package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.User;

import java.util.List;
import java.util.Optional;

public interface LoginRepository {
    void saveUser(String sessionId, User user);
    void deleteUser(String sessionId);
    int getConcurrentUserCount(String userId);
    User getLoginUserById(String sessionId);
    List<User> getUserList();
}
