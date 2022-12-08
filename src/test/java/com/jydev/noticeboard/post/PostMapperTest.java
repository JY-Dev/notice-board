package com.jydev.noticeboard.post;

import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.PostUser;
import com.jydev.noticeboard.post.util.PostDependency;
import com.jydev.noticeboard.post.util.PostMockFactory;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class PostMapperTest {
    private final PostMapper postMapper = PostDependency.postMapper;

    @Test
    void PostEntityToPostTest(){
        Post post = postMapper.toPost(PostMockFactory.makePostEntity(), Collections.emptyList());
        Post result = PostMockFactory.makePost();
        Assertions.assertThat(result).isEqualTo(post);
    }

    @Test
    void userEntityToPostUser(){
        UserEntity userEntity = UserMockFactory.makeUserEntity();
        PostUser postUser = postMapper.toPostUser(userEntity);
        PostUser result = PostMockFactory.makePostUser();
        Assertions.assertThat(result).isEqualTo(postUser);
    }
}
