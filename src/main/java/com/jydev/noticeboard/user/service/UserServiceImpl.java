package com.jydev.noticeboard.user.service;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.repository.LoginRepository;
import com.jydev.noticeboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final UserMapper userMapper;
    private static final int CONCURRENT_MAX = 2;


    @Override
    public User login(String sessionId,String userId, String userPassword) {
        User user = userRepository.findById(userId)
                .filter(userEntity -> userEntity.getPassword().equals(userPassword))
                .map(userMapper::toUser)
                .orElse(null);

        if(isPossibleLogin(user)){
            loginRepository.saveUser(sessionId,user);
            return user;
        } else
            return null;
    }

    @Override
    public void logout(String sessionId) {
        loginRepository.deleteUser(sessionId);
    }

    @Override
    public List<User> getLoginUsers() {
        return loginRepository.getUserList();
    }

    private boolean isPossibleLogin(User user){
        return user != null && loginRepository.getConcurrentUserCount(user.getId()) < CONCURRENT_MAX;
    }


    @Override
    public User registerUser(UserRegisterRequest request) {
        return userMapper.toUser(userRepository.saveUser(request));
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
