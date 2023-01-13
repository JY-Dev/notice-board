package com.jydev.noticeboard.post;

import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.model.request.PostSearchRequest;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.post.service.PostService;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.post.util.PostDependency;
import com.jydev.noticeboard.post.util.PostMockFactory;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PostMapper postMapper;

    @BeforeEach
    void registerUser(){
        userRepository.saveUser(UserMockFactory.makeUserRegisterRequest());
    }

    @Test
    public void registerPostTest(){
        PostRequest postRequest = PostMockFactory.makePostRequest();
        Optional<Post> result = postService.registerPost(postRequest);
        Post post = PostMockFactory.makePost(result.get().getId());
        Assertions.assertThat(result.orElse(null)).isEqualTo(post);
    }

    @Test
    public void deletePostTest(){
        Optional<Post> post = postService.registerPost(PostMockFactory.makePostRequest());
        postService.deletePostById(post.get().getId());
        Assertions.assertThat(postService.getPost(post.get().getId()).orElse(null)).isNull();
    }

    @Test
    public void editPostTest(){
        Post post = postService.registerPost(PostMockFactory.makePostRequest()).get();
        PostEntity postEntity = PostMockFactory.makePostEntity(post.getId());
        postEntity.setTitle(PostData.changeTitle);
        postEntity.setContent(PostData.changeContent);
        Post editPost = postMapper.toPost(postEntity);
        postService.updatePost(PostMockFactory.makePostEditRequest(post.getId()));
        Optional<Post> result = postService.getPost(post.getId());
        Assertions.assertThat(result.orElse(null)).isEqualTo(editPost);

    }

    @Test
    public void findPagePostsTest(){
        int pageNum = 2;
        int pageSize = 10;
        for (int i = 0; i < PostData.PAGE_POSTS_MAX_SIZE; i++) {
            postService.registerPost(PostMockFactory.makePostRequest());
        }
        List<PagePost> result = postService.findPagePosts(PostMockFactory.makePostSearchRequest(pageNum,pageSize));
        for(int i = 0; i < pageSize; i++){
            Assertions.assertThat(i+pageNum*pageSize+1L).isEqualTo(result.get(i).getId());
        }
    }

    @Test
    public void findPagePostsFilterTest(){
        int keywordDataSize = 10;
        for (int i = 0; i < keywordDataSize; i++) {
            postService.registerPost(PostMockFactory.makePostRequest());
        }
        for (int i = 0; i < PostData.PAGE_POSTS_MAX_SIZE; i++) {
            postService.registerPost(PostMockFactory.makePostEmptyTitleRequest());
        }
        List<PagePost> allResult = postService.findPagePosts(PostMockFactory.makePostAllSearchRequest());
        Assertions.assertThat(allResult.size()).isEqualTo(PostData.PAGE_POSTS_MAX_SIZE);
        List<PagePost> filterResult = postService.findPagePosts(PostMockFactory.makePostKeywordSearchRequest());
        Assertions.assertThat(filterResult.size()).isEqualTo(keywordDataSize);
    }

    @Test
    public void getPageIndicatorTest(){
        for (int i = 0; i < 100; i++) {
            postService.registerPost(PostMockFactory.makePostRequest());
        }
        PostSearchRequest postSearchRequest = PostMockFactory.makePostAllSearchRequest(3);
        List<PagePost> pagePosts = postService.findPagePosts(postSearchRequest);
        List<Long> pageIndicator = postService.getPageIndicator(postSearchRequest, pagePosts.size());
        Assertions.assertThat(pageIndicator.size()).isEqualTo(PostService.indicatorSize);
        for (int i = 0; i < 5; i++) {
            Assertions.assertThat(pageIndicator.get(i)).isEqualTo(i+1);
        }
    }

    @Test
    public void getPageIndicatorLackIndicatorSizeTest(){
        for (int i = 0; i < 5; i++) {
            postService.registerPost(PostMockFactory.makePostRequest());
        }
        PostSearchRequest postSearchRequest = PostMockFactory.makePostAllSearchRequest(1);
        List<PagePost> pagePosts = postService.findPagePosts(postSearchRequest);
        List<Long> pageIndicator = postService.getPageIndicator(postSearchRequest, pagePosts.size());
        Assertions.assertThat(pageIndicator.size()).isEqualTo(1);
    }
}
