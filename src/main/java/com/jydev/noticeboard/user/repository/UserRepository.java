package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserEntity> findAllUsers();
    UserEntity findById(String userId);
    UserEntity saveUser(UserRegisterRequest request);
    void deleteUserById(String userId);
}
