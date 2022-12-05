package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.user.model.User;

public interface PostRepository {
    PostEntity savePost(PostRequest request, User registerUser);
    PostEntity findPostById(Long postId);
    void deletePostById(Long postId);
}
