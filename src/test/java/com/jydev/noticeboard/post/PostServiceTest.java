package com.jydev.noticeboard.post;

import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.service.PostService;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.post.util.PostDependency;
import com.jydev.noticeboard.post.util.PostMockFactory;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class PostServiceTest {
    private final PostService postService = PostDependency.getPostService();
    private final PostMapper postMapper = PostDependency.postMapper;

    @BeforeAll
    static void init(){
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        PostDependency.userRepository.saveUser(request);
    }

    @BeforeEach
    void clearStore(){
        PostDependency.postRepository.deleteAllPost();
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

    @Test
    public void editPostTest(){
        postService.registerPost(PostMockFactory.makePostRequest());
        PostEntity postEntity = PostMockFactory.makePostEntity();
        postEntity.setTitle(PostData.changeTitle);
        postEntity.setContent(PostData.changeContent);
        Post editPost = postMapper.toPost(postEntity, Collections.emptyList());
        postService.updatePost(PostMockFactory.makePostEditRequest());
        Optional<Post> result = postService.getPost(PostData.postId);
        Assertions.assertThat(result.orElse(null)).isEqualTo(editPost);

    }

    @Test
    public void findPagePostsTest(){
        for (int i = 0; i < PostData.PAGE_POSTS_MAX_SIZE; i++) {
            postService.registerPost(PostMockFactory.makePostRequest());
        }
        List<PagePost> data = PostMockFactory.makePagePosts();
        List<PagePost> result = postService.findPagePosts(PostMockFactory.makePostAllSearchRequest());
        Assertions.assertThat(data).isEqualTo(result);
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
    public void findPagePostsLackTest(){
        int dataSize = 5;
        for (int i = 0; i < dataSize; i++) {
            postService.registerPost(PostMockFactory.makePostRequest());
        }
        List<PagePost> allResult = postService.findPagePosts(PostMockFactory.makePostLackSearchRequest());
        Assertions.assertThat(allResult.size()).isEqualTo(0);
    }
}
