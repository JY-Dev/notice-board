package com.jydev.noticeboard.post.service;

import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.model.request.PostSearchRequest;

import java.util.List;
import java.util.Optional;

public interface PostService {
    static int indicatorSize = 5;
    Optional<Post> registerPost(PostRequest request);
    void deletePostById(Long postId);
    Optional<Post> getPost(Long postId);
    void updatePost(PostEditRequest request);

    List<PagePost> findPagePosts(PostSearchRequest request);

    List<Long> getPageIndicator(PostSearchRequest request, int pagePostsSize);
}
