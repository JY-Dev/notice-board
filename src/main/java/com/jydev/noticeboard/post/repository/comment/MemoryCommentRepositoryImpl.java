package com.jydev.noticeboard.post.repository.comment;

import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@RequiredArgsConstructor
@Repository
public class MemoryCommentRepositoryImpl implements CommentRepository {
    Map<Long, CommentEntity> commentStore = new ConcurrentHashMap<>();

    AtomicLong idCnt = new AtomicLong();

    @Override
    public List<CommentEntity> findCommentsByPostId(Long postId) {
        return commentStore.values().stream()
                .filter(commentEntity -> commentEntity.getPostId().equals(postId))
                .toList();
    }

    @Override
    public CommentEntity saveComment(CommentRequest commentRequest, UserEntity userEntity) {
        long commentId = idCnt.getAndAdd(1);

        CommentEntity commentEntity = new CommentEntity(userEntity,commentRequest.getPostId(),commentId
                ,commentRequest.getParentId(),commentRequest.getContent(), LocalDateTime.now());
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
