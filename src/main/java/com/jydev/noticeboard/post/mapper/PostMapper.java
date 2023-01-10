package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.PostUser;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post toPost(PostEntity postEntity, List<MappingCommentHierarchy> comments) {
        int commentsSize = (int) comments.stream().map(MappingCommentHierarchy::getChildComments)
                .filter(childComments -> !childComments.isEmpty()).count() + comments.size();
        return new Post(postEntity.getId(), toPostUser(postEntity.getUser()),
                postEntity.getTitle(), postEntity.getContent(), postEntity.getCreatedDateTime(), commentsSize, comments);
    }

    public PostUser toPostUser(UserEntity userEntity) {
        return new PostUser(userEntity.getProfileImageUrl(), userEntity.getNickname(), userEntity.getId());
    }

    public PagePost toPagePost(PostEntity postEntity) {
        return new PagePost(postEntity.getId(), toPostUser(postEntity.getUser()),
                postEntity.getTitle(), postEntity.getContent(), postEntity.getCreatedDateTime());
    }
}
