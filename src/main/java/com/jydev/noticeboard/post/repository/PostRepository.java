package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.user.model.entity.UserEntity;

public interface PostRepository {
    PostEntity savePost(PostRequest request, UserEntity registerUser);
    PostEntity findPostById(Long postId);
    void deletePostById(Long postId);

    PostEntity updatePost(PostEditRequest request);
}
