package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.User;

import java.util.List;

public interface LoginRepository {
    void saveUser(String sessionId, User user);
    void deleteUser(String sessionId);
    int getConcurrentUserCount(String userId);
    List<User> getUserList();
}
