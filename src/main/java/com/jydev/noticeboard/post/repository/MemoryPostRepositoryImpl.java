package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.user.model.UserRole;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryPostRepositoryImpl implements PostRepository {
    private final Map<Long, PostEntity> storePost = new HashMap<>();
    private final AtomicLong idCnt = new AtomicLong();

    public MemoryPostRepositoryImpl(){
        long postId = idCnt.get();
        idCnt.set(postId+1);
        PostRequest postRequest = new PostRequest("안녕","컨텐츠","1234");
        UserEntity userEntity = new UserEntity("1234@1234","야옹이","1234","1234", UserRole.SUPER_ADMIN);
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

}
