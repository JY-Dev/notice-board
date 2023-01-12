package com.jydev.noticeboard.post.comment;

import com.jydev.noticeboard.post.comment.util.CommentDependency;
import com.jydev.noticeboard.post.comment.util.CommentMockFactory;
import com.jydev.noticeboard.post.mapper.CommentMapper;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.CommentUser;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentMapperTest {
    private final CommentMapper commentMapper = CommentDependency.commentMapper;

    @Test
    void commentEntityToCommentTest(){
        CommentEntity commentEntity = CommentMockFactory.makeCommentEntity(PostData.postId);
        Comment comment = commentMapper.toComment(commentEntity);
        Comment result = CommentMockFactory.makeComment(PostData.postId);
        Assertions.assertThat(result).isEqualTo(comment);
    }

    @Test
    void userEntityToCommentUserTest(){
        UserEntity userEntity = UserMockFactory.makeUserEntity();
        CommentUser commentUser = commentMapper.toCommentUser(userEntity);
        CommentUser result = CommentMockFactory.makeCommentUser();
        Assertions.assertThat(result).isEqualTo(commentUser);
    }

}
