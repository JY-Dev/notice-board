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
import java.util.Collections;
import java.util.List;

public class CommentMockFactory {

    public static CommentRequest makeCommentRequest(Long postId,Long parentId){
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPostId(postId);
        commentRequest.setUserId(UserData.userId);
        commentRequest.setContent("");
        commentRequest.setParentId(parentId);
        return commentRequest;
    }

    public static CommentEntity makeCommentEntity( Long postId){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(CommentData.commentId);
        commentEntity.setUserEntity(UserMockFactory.makeUserEntity());
        commentEntity.setPost(PostMockFactory.makePostEntity(postId));
        commentEntity.setContent("");
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        return commentEntity;
    }
    public static Comment makeComment(Long postId){
        return new Comment(CommentData.commentId,"", LocalDateTime.now(), Collections.emptyList(),makeCommentUser(),postId);
    }

    public static Comment makeComment( Long commentId, Long postId){
        return new Comment(commentId,"", LocalDateTime.now(), Collections.emptyList(),makeCommentUser(),postId);
    }

    public static CommentUser makeCommentUser(){
        return new CommentUser(UserData.userId,UserData.nickname,UserData.profileImageUrl);
    }
}
