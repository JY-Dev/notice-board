package com.jydev.noticeboard.post.comment;

import com.jydev.noticeboard.post.comment.util.CommentData;
import com.jydev.noticeboard.post.comment.util.CommentDependency;
import com.jydev.noticeboard.post.comment.util.CommentMockFactory;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.service.comment.CommentService;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.util.UserMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CommentServiceTest {
    private CommentService commentService;

    @BeforeEach
    void init(){
        commentService = CommentDependency.getCommentService();
        UserRegisterRequest request = UserMockFactory.makeUserRegisterRequest();
        CommentDependency.userRepository.saveUser(request);
    }


    @Test
    void registerCommentTest(){
        CommentRequest commentRequest = CommentMockFactory.makeCommentRequest(-1L);
        Comment comment = CommentMockFactory.makeComment(-1L);
        commentService.registerComment(commentRequest);
        List<MappingCommentHierarchy> comments = commentService.getComments(PostData.postId);
        Assertions.assertThat(comment).isEqualTo(comments.get(0).getParentComment());
    }

    @Test
    void getCommentsTest(){
        CommentRequest commentRequest = CommentMockFactory.makeCommentRequest(-1L);
        CommentRequest commentRequest1 = CommentMockFactory.makeCommentRequest(CommentData.commentId);
        CommentRequest commentRequest2 = CommentMockFactory.makeCommentRequest(CommentData.commentId);
        commentService.registerComment(commentRequest);
        commentService.registerComment(commentRequest1);
        commentService.registerComment(commentRequest2);
        MappingCommentHierarchy mappingCommentHierarchy = CommentMockFactory.makeMappingCommentHierarchy();
        MappingCommentHierarchy comment = commentService.getComments(PostData.postId).get(0);
        Assertions.assertThat(mappingCommentHierarchy).isEqualTo(comment);
    }

    @Test
    void deleteCommentTest(){
        CommentRequest commentRequest = CommentMockFactory.makeCommentRequest(-1L);
        commentService.registerComment(commentRequest);
        Comment comment = CommentMockFactory.makeComment(-1L);
        List<MappingCommentHierarchy> comments = commentService.getComments(PostData.postId);
        Assertions.assertThat(comment).isEqualTo(comments.get(0).getParentComment());
        commentService.deleteComment(CommentData.commentId);
        List<MappingCommentHierarchy> deleteComments = commentService.getComments(PostData.postId);
        Assertions.assertThat(deleteComments.size()).isEqualTo(0);
    }
}
