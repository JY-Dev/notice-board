package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.PostUser;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post toPost(PostEntity postEntity, List<MappingCommentHierarchy> comments) {
        return new Post(postEntity.getId(), toPostUser(postEntity.getRegisterUser()),
                postEntity.getTitle(), postEntity.getContent(),postEntity.getRegisterDateTime(),comments);
    }

    public PostUser toPostUser(UserEntity userEntity){
        return new PostUser(userEntity.getImageUrl(),userEntity.getNickname(),userEntity.getId());
    }
}
