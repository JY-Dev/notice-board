package com.jydev.noticeboard.user.util;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.repository.LoginRepository;
import com.jydev.noticeboard.user.repository.LoginRepositoryImpl;
import com.jydev.noticeboard.user.repository.MemoryUserRepository;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.service.UserServiceImpl;

public class UserDependency {
    public static final UserMapper userMapper = new UserMapper();
    public static final UserRepository userRepository = new MemoryUserRepository(userMapper);
    public static final LoginRepository loginRepository = new LoginRepositoryImpl();
    public static final UserService userService = new UserServiceImpl(userRepository,loginRepository,userMapper);
}
