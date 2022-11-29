package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class MemoryUserRepository implements UserRepository {
    private final UserMapper userMapper;
    private final Map<String, UserEntity> userStore = new ConcurrentHashMap<>();

    @Override
    public List<UserEntity> findAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public Optional<UserEntity> findById(String userId) {
        return Optional.ofNullable(userStore.get(userId));
    }

    @Override
    public Optional<UserEntity> saveUser(UserRegisterRequest request) {
        UserEntity userEntity =userMapper.toEntity(request);
        Optional<UserEntity> findUser = findById(userEntity.getId());
        if(findUser.isPresent())
            return Optional.empty();
        userStore.put(request.getId(), userEntity);
        return Optional.of(userEntity);
    }

    @Override
    public void deleteUserById(String userId) {
        userStore.remove(userId);
    }
}
