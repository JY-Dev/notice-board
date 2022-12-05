package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;

public interface PostRepository {
    PostEntity savePost();
    PostEntity findPostById(Long postId);
    void deletePostById(Long postId);
}
