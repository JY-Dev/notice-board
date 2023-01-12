package com.jydev.noticeboard.post.repository.comment;

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

    AtomicLong idCnt = new AtomicLong();

    @Override
    public CommentEntity saveComment(CommentRequest commentRequest, CommentEntity parentComment, PostEntity postEntity, UserEntity userEntity) {
        long commentId = idCnt.getAndAdd(1);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setParent(parentComment);
        commentEntity.setId(commentId);
        commentEntity.setPost(postEntity);
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        commentStore.put(commentId,commentEntity);
        return commentEntity;
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentStore.remove(commentId);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return commentStore.get(commentId);
    }
}
