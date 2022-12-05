package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Repository
public class MemoryPostRepositoryImpl implements PostRepository {
    private final Map<Long, PostEntity> storePost = new HashMap<>();
    private final AtomicLong idCnt = new AtomicLong();

    @Override
    public PostEntity savePost(PostRequest request, UserEntity registerUser) {
        long postId = idCnt.get();
        idCnt.set(postId+1);
        PostEntity postEntity = new PostEntity(postId, request.getTitle(), request.getContent(), registerUser);
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
