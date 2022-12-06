package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.UserRole;
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
public class MemoryUserRepository implements UserRepository {
    private final UserMapper userMapper;
    private final Map<String, UserEntity> userStore = new ConcurrentHashMap<>();

    public MemoryUserRepository(UserMapper userMapper){
        this.userMapper = userMapper;
        userStore.put("1234",new UserEntity("1234@1234","야옹이","1234","1234", UserRole.SUPER_ADMIN));
    }


    @Override
    public List<UserEntity> findAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public UserEntity findById(String userId) {
        return userStore.get(userId);
    }

    @Override
    public UserEntity saveUser(UserRegisterRequest request) {
        UserEntity userEntity =userMapper.toEntity(request);
        userStore.put(request.getId(), userEntity);
        return userEntity;
    }

    @Override
    public void deleteUserById(String userId) {
        userStore.remove(userId);
    }
}
