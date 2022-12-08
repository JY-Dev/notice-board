package com.jydev.noticeboard.post;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.service.PostService;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.post.util.PostDependency;
import com.jydev.noticeboard.post.util.PostMockFactory;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;


public class PostServiceTest {
    private final PostService postService = PostDependency.getPostService();

    @BeforeAll
    static void init(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        PostDependency.userRepository.saveUser(request);
    }

    @Test
    public void registerPostTest(){
        PostRequest postRequest = PostMockFactory.makePostRequest();
        Optional<Post> result = postService.registerPost(postRequest);
        Post post = PostMockFactory.makePost();
        Assertions.assertThat(result.orElse(null)).isEqualTo(post);
    }

    @Test
    public void deletePostTest(){
        Optional<Post> post = postService.registerPost(PostMockFactory.makePostRequest());
        Assertions.assertThat(post.orElse(null)).isEqualTo(PostMockFactory.makePost());
        postService.deletePostById(PostData.postId);
        Assertions.assertThat(postService.getPost(PostData.postId).orElse(null)).isNull();
    }

}
