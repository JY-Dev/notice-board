package com.jydev.noticeboard.post.comment.util;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.CommentUser;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.util.PostData;
import com.jydev.noticeboard.post.util.PostMockFactory;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserMockFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentMockFactory {

    public static CommentRequest makeCommentRequest(Long parentId){
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPostId(PostData.postId);
        commentRequest.setUserId(UserData.userId);
        commentRequest.setContent("");
        commentRequest.setParentId(parentId);
        return commentRequest;
    }

    public static CommentEntity makeCommentEntity(Long parentId, Long postId){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(CommentData.commentId);
        commentEntity.setUserEntity(UserMockFactory.makeUserEntity());
        commentEntity.setPost(PostMockFactory.makePostEntity(postId));
        commentEntity.setContent("");
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        return commentEntity;
    }
    public static Comment makeComment(Long parentId){
        return new Comment(CommentData.commentId,parentId,"", LocalDateTime.now(),makeCommentUser());
    }

    public static MappingCommentHierarchy makeMappingCommentHierarchy(){
        return new MappingCommentHierarchy(makeComment(-1L),makeChildComments());
    }

    public static List<Comment> makeChildComments(){
        List<Comment> childComments = new ArrayList<>();
        Comment comment = makeComment(CommentData.commentId);
        Comment comment1 = makeComment(CommentData.commentId);
        childComments.add(comment);
        childComments.add(comment1);
        return childComments;
    }

    public static List<CommentEntity> makeChildCommentEntities(Long postId){
        List<CommentEntity> childComments = new ArrayList<>();
        CommentEntity comment = makeCommentEntity(CommentData.commentId,postId);
        CommentEntity comment1 = makeCommentEntity(CommentData.commentId,postId);
        childComments.add(comment);
        childComments.add(comment1);
        return childComments;
    }

    public static CommentUser makeCommentUser(){
        return new CommentUser(UserData.userId,UserData.nickname,UserData.profileImageUrl);
    }
}
