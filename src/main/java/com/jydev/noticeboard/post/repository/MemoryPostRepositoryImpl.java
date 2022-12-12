package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.model.request.PostSearchRequest;
import com.jydev.noticeboard.user.model.Mapper.UserMapper;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryPostRepositoryImpl implements PostRepository {
    private final Map<Long, PostEntity> storePost = new HashMap<>();
    private AtomicLong idCnt = new AtomicLong();


    public MemoryPostRepositoryImpl(UserMapper userMapper){
        String[] titleArr = {"고양이","강아지","바다","강사","고라니","강바다씨","코끼리","고구마","고사리"};
        List<String> titleList = Arrays.asList(titleArr);
        for (int i = 0; i < 100; i++) {
            long postId = idCnt.getAndAdd(1);
            Collections.shuffle(titleList);
            PostRequest postRequest = new PostRequest(titleList.get(0),"컨텐츠","1234");
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
    public void updatePost(PostEditRequest request) {
        Long postId = request.getId();
        PostEntity postEntity = storePost.get(postId);
        if(postEntity == null)
            return;
        postEntity.setContent(request.getContent());
        postEntity.setTitle(request.getTitle());
        storePost.put(postId,postEntity);
    }

    @Override
    public void deleteAllPost() {
        idCnt = new AtomicLong();
        storePost.clear();
    }

    @Override
    public List<PostEntity> findAllPost(PostSearchRequest request) {
        int pageNumWeight = Math.max(request.getPageNum()-1,0);
        int pageSize = request.getPageSize();
        String keyword = request.getKeyword();
        int startIndex = pageNumWeight*pageSize;
        int lastIndex = startIndex+pageSize;
        List<PostEntity> filterList = storePost.values().stream()
                .filter(postEntity -> isFilterKeyword(keyword,postEntity.getTitle()))
                .sorted((one,two) -> two.getId().compareTo(one.getId()))
                .toList();
        if(filterList.size() > startIndex){
            if(filterList.size() > lastIndex)
                return filterList.subList(startIndex,lastIndex);
            else
                return filterList.subList(startIndex,filterList.size());
        }
        return Collections.emptyList();
    }

    @Override
    public int getTotalPostsSize() {
        return storePost.size();
    }

    private boolean isFilterKeyword(String keyword, String target){
        return keyword.isEmpty() || target.contains(keyword);
    }

}
