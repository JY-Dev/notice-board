package com.jydev.noticeboard.post.repository.comment;

import com.jydev.noticeboard.post.model.comment.entity.ChildCommentEntity;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@RequiredArgsConstructor
//@Repository
public class MemoryCommentRepositoryImpl implements CommentRepository {

    Map<Long, CommentEntity> commentStore = new ConcurrentHashMap<>();
    Map<Long, ChildCommentEntity> childCommentStore = new ConcurrentHashMap<>();

    AtomicLong idCnt = new AtomicLong();

    @Override
    public CommentEntity saveComment(CommentRequest commentRequest, PostEntity postEntity, UserEntity userEntity) {
        long commentId = idCnt.getAndAdd(1);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(commentId);
        commentEntity.setPost(postEntity);
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        commentStore.put(commentId,commentEntity);
        return commentEntity;
    }

    @Override
    public ChildCommentEntity saveChildComment(CommentRequest commentRequest, CommentEntity parentComment, PostEntity postEntity, UserEntity userEntity) {
        long commentId = idCnt.getAndAdd(1);
        ChildCommentEntity commentEntity = new ChildCommentEntity();
        commentEntity.setId(commentId);
        commentEntity.setParent(parentComment);
        commentEntity.setPost(postEntity);
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        childCommentStore.put(commentId,commentEntity);
        return commentEntity;
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentStore.remove(commentId);
    }

    @Override
    public void deleteChildCommentById(Long commentId) {
        childCommentStore.remove(commentId);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return commentStore.get(commentId);
    }

    @Override
    public ChildCommentEntity getChildCommentById(Long commentId) {
        return childCommentStore.get(commentId);
    }
}
