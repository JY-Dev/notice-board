package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toPost(PostEntity postEntity) {
        return new Post(postEntity.getId(), postEntity.getRegisterUser().getNickname(),
                postEntity.getRegisterUser().getId(),
                postEntity.getTitle(), postEntity.getContent(),postEntity.getRegisterDateTime());
    }
}
