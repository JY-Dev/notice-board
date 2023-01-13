package com.jydev.noticeboard.post.comment;

import com.jydev.noticeboard.post.comment.util.CommentData;
import com.jydev.noticeboard.post.comment.util.CommentDependency;
import com.jydev.noticeboard.post.comment.util.CommentMockFactory;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.repository.comment.CommentRepository;
import com.jydev.noticeboard.post.service.PostService;
import com.jydev.noticeboard.post.service.comment.CommentService;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.post.util.PostMockFactory;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;


    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    private Post post;

    @BeforeEach
    void init(){
        userService.registerUser(UserMockFactory.makeUserRegisterRequest());
        post = postService.registerPost(PostMockFactory.makePostRequest()).get();
    }


    @Test
    void registerCommentTest(){
        CommentRequest commentRequest = CommentMockFactory.makeCommentRequest(post.getId(),-1L);
        Optional<Comment> result = commentService.registerComment(commentRequest);
        Comment comment = CommentMockFactory.makeComment(result.get().getId(),result.get().getPostId());
        Assertions.assertThat(comment).isEqualTo(result.get());
    }

    @Test
    void registerChildCommentsTest(){
        Optional<Comment> comment = commentService.registerComment(CommentMockFactory.makeCommentRequest(post.getId(),-1L));
        Long parentId = comment.get().getId();
        int childCommentsSize = 10;
        for (int i = 0; i < childCommentsSize; i++) {
            commentService.registerComment(CommentMockFactory.makeCommentRequest(post.getId(),parentId));
        }
        Comment data = commentService.getComment(-1L,parentId).get();
        Assertions.assertThat(data.getChildComments().size()).isEqualTo(childCommentsSize);
    }

    @Test
    void deleteCommentTest(){
        Optional<Comment> comment = commentService.registerComment(CommentMockFactory.makeCommentRequest(post.getId(),-1L));
        Assertions.assertThat(commentService.getComment(-1L,comment.get().getId()).orElse(null)).isNotNull();
        commentService.deleteComment(-1L,comment.get().getId());
        Assertions.assertThat(commentService.getComment(-1L,comment.get().getId()).orElse(null)).isNull();
    }

    @Test
    void deleteChildCommentTest(){
        Optional<Comment> comment = commentService.registerComment(CommentMockFactory.makeCommentRequest(post.getId(),-1L));
        Assertions.assertThat(commentService.getComment(-1L,comment.get().getId()).orElse(null)).isNotNull();

        Optional<Comment> childComment = commentService.registerComment(CommentMockFactory.makeCommentRequest(post.getId(),comment.get().getId()));
        Assertions.assertThat(commentService.getComment(comment.get().getId(), childComment.get().getId()).orElse(null)).isNotNull();

        commentService.deleteComment(comment.get().getId(), childComment.get().getId());
        Assertions.assertThat(commentService.getComment(comment.get().getId(), childComment.get().getId()).orElse(null)).isNull();
    }
}
