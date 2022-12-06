package com.jydev.noticeboard.user.util;

import com.jydev.noticeboard.file.ImageFileSystem;
import com.jydev.noticeboard.file.util.FileDependency;
import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.repository.LoginRepository;
import com.jydev.noticeboard.user.repository.LoginRepositoryImpl;
import com.jydev.noticeboard.user.repository.MemoryUserRepository;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.service.UserServiceImpl;

public class UserDependency {
    public static final UserMapper userMapper = new UserMapper(FileDependency.imageFileSystem);

    public static UserRepository getUserRepository(){
        return new MemoryUserRepository(userMapper);
    }

    public static LoginRepository getLoginRepository(){
        return new LoginRepositoryImpl();
    }

    public static UserService getUserService(){
        return new UserServiceImpl(getUserRepository(),getLoginRepository(),userMapper);
    }
}
