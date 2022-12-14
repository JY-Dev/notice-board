package com.jydev.noticeboard.user.service;

import com.jydev.noticeboard.user.model.LoginStatus;
import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.repository.LoginRepository;
import com.jydev.noticeboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final UserMapper userMapper;
    private static final int CONCURRENT_MAX = 2;


    @Override
    public LoginStatus login(String sessionId, String userId, String userPassword) {
        UserEntity userEntity = userRepository.findById(userId);
        if(userEntity == null ||userEntity.getId().equals(userId) && !userPassword.equals(userEntity.getPassword()))
            return LoginStatus.INVALID;
        if(isUserLoginConcurrencyMax(userId))
            return LoginStatus.CONCURRENCY_MAX;
        else {
            loginRepository.saveUser(sessionId, userMapper.toUser(userEntity));
            return LoginStatus.SUCCESS;
        }
    }

    @Override
    public void logout(String sessionId) {
        loginRepository.deleteUser(sessionId);
    }

    @Override
    public Optional<User> getLoginUser(String sessionId) {
        return Optional.ofNullable(loginRepository.getLoginUserById(sessionId));
    }

    @Override
    public List<User> getLoginUsers() {
        return loginRepository.getUserList();
    }

    private boolean isUserLoginConcurrencyMax(String userId){
        return loginRepository.getConcurrentUserCount(userId) >= CONCURRENT_MAX;
    }

    @Override
    public Optional<User> registerUser(UserRegisterRequest request) {
        UserEntity user = userRepository.saveUser(request);
        return Optional.ofNullable(user)
                .map(userMapper::toUser);
    }

    @Override
    public List<User> getRegisterUsers() {
        return userRepository.findAllUsers().stream().map(userMapper::toUser).toList();
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUserById(userId);
    }
}
