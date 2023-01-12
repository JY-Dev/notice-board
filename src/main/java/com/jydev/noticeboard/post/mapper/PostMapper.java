package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.PostUser;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final CommentMapper commentMapper;
    public Post toPost(PostEntity postEntity) {
        int commentsSize = (int) postEntity.getComments().stream().map(commentEntity -> commentEntity.getChild().size()+1).count();
        List<Comment> comments = postEntity.getComments().stream().map(commentMapper::toComment).toList();
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
