package com.jydev.noticeboard.user.repository;

import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final EntityManager em;
    private final UserMapper userMapper;
    @Override
    public List<UserEntity> findAllUsers() {
        String jpql = "select u from UserEntity u";
        return em.createQuery(jpql, UserEntity.class).getResultList();
    }

    @Override
    public UserEntity findById(String userId) {
        return em.find(UserEntity.class,userId);
    }

    @Override
    public UserEntity saveUser(UserRegisterRequest request) {
        UserEntity userEntity = userMapper.toEntity(request);
        em.persist(userEntity);
        return userEntity;
    }

    @Override
    public void deleteUserById(String userId) {
        UserEntity user = em.find(UserEntity.class, userId);
        em.remove(user);
    }
}
