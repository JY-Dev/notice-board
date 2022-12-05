package com.jydev.noticeboard.post.service;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.request.PostRequest;

import java.util.Optional;

public interface PostService {
    Optional<Post> savePost(PostRequest request);
    void deletePostById(Long postId);
    Optional<Post> findPostById(Long postId);
}
