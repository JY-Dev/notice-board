package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryPostRepositoryImpl implements PostRepository {
    private final Map<Long, PostEntity> storePost = new HashMap<>();
    private final AtomicLong idCnt = new AtomicLong();

    public MemoryPostRepositoryImpl(UserMapper userMapper){
        long postId = idCnt.getAndAdd(1);
        PostRequest postRequest = new PostRequest("안녕","컨텐츠","1234");
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setNickname("야옹이");
        userRegisterRequest.setId("1234");
        userRegisterRequest.setEmail("1234@1234");
        userRegisterRequest.setPassword("1234");
        userRegisterRequest.setConfirmPassword("1234");
        UserEntity userEntity = userMapper.toEntity(userRegisterRequest);
        PostEntity postEntity = new PostEntity(postId,postRequest.getTitle(),postRequest.getContent(),LocalDateTime.now(),userEntity);
        storePost.put(postId,postEntity);
    }

    @Override
    public PostEntity savePost(PostRequest request, UserEntity registerUser) {
        long postId = idCnt.get();
        idCnt.set(postId+1);
        PostEntity postEntity = new PostEntity(postId, request.getTitle(), request.getContent(), LocalDateTime.now(), registerUser);
        storePost.put(postId,postEntity);
        return postEntity;
    }

    @Override
    public PostEntity findPostById(Long postId) {
        return storePost.get(postId);
    }

    @Override
    public void deletePostById(Long postId) {
        storePost.remove(postId);
    }

    @Override
    public PostEntity updatePost(PostEditRequest request) {
        Long postId = request.getId();
        PostEntity postEntity = storePost.get(postId);
        if(postEntity == null)
            return null;
        postEntity.setContent(request.getContent());
        postEntity.setTitle(request.getTitle());
        storePost.put(postId,postEntity);
        return postEntity;
    }

}
