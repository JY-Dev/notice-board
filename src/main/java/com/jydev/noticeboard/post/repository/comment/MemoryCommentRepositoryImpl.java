package com.jydev.noticeboard.post.repository.comment;

import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class MemoryCommentRepositoryImpl implements CommentRepository {
    Map<Long, CommentEntity> map = new ConcurrentHashMap<>();

    AtomicLong idCnt = new AtomicLong();

    @Override
    public List<CommentEntity> findCommentsByPostId(Long postId) {
        return map.values().stream()
                .filter(commentEntity -> commentEntity.getPostId().equals(postId))
                .toList();
    }

    @Override
    public CommentEntity saveComment(CommentRequest commentRequest) {
        long commentId = idCnt.getAndAdd(1);
        CommentEntity commentEntity = new CommentEntity(commentRequest.getPostId(),commentId
                ,commentRequest.getParentId(),commentRequest.getContent(), LocalDateTime.now());
        map.put(commentId,commentEntity);
        return commentEntity;
    }

    @Override
    public void deleteCommentById(Long commentId) {
        map.remove(commentId);
    }
}
