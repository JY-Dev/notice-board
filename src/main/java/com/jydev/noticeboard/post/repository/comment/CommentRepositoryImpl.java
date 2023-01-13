package com.jydev.noticeboard.post.repository.comment;

import com.jydev.noticeboard.post.model.comment.entity.ChildCommentEntity;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{
    private final EntityManager em;


    @Override
    public CommentEntity saveComment(CommentRequest commentRequest, PostEntity postEntity, UserEntity userEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setPost(postEntity);
        commentEntity.setUserEntity(userEntity);
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        em.persist(commentEntity);
        return commentEntity;
    }

    @Override
    public ChildCommentEntity saveChildComment(CommentRequest commentRequest, CommentEntity parentComment, PostEntity postEntity, UserEntity userEntity) {
        ChildCommentEntity commentEntity = new ChildCommentEntity();
        commentEntity.registerChildComment(parentComment);
        commentEntity.setPost(postEntity);
        commentEntity.setUserEntity(userEntity);
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setCreatedDateTime(LocalDateTime.now());
        em.persist(commentEntity);
        return commentEntity;
    }

    @Override
    public void deleteCommentById(Long commentId) {
        CommentEntity commentEntity = em.find(CommentEntity.class, commentId);
        em.remove(commentEntity);
    }

    @Override
    public void deleteChildCommentById(Long commentId) {
        ChildCommentEntity commentEntity = em.find(ChildCommentEntity.class, commentId);
        em.remove(commentEntity);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return em.find(CommentEntity.class, commentId);
    }

    @Override
    public ChildCommentEntity getChildCommentById(Long commentId) {
        return em.find(ChildCommentEntity.class, commentId);
    }
}
