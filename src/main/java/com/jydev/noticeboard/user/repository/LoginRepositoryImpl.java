package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LoginRepositoryImpl implements LoginRepository{
    private static final Map<String,User> userStore = new ConcurrentHashMap<>();
    @Override
    public void saveUser(String sessionId, User user) {
        userStore.put(sessionId,user);
    }

    @Override
    public void deleteUser(String sessionId) {
        userStore.remove(sessionId);
    }

    @Override
    public int getConcurrentUserCount(String userId) {
        return userStore.values().stream()
                .filter(user -> user.getId().equals(userId))
                .toArray().length;
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<>(userStore.values());
    }
}
