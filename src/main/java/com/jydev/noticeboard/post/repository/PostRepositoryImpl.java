package com.jydev.noticeboard.post.repository;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.model.request.PostSearchRequest;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository{

    private final EntityManager em;
    @Override
    public PostEntity savePost(PostRequest request, UserEntity registerUser) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(request.getTitle());
        postEntity.setContent(request.getContent());
        postEntity.setCreatedDateTime(LocalDateTime.now());
        postEntity.setUser(registerUser);
        em.persist(postEntity);
        return postEntity;
    }

    @Override
    public PostEntity findPostById(Long postId) {
        return em.find(PostEntity.class, postId);
    }

    @Override
    public void deletePostById(Long postId) {
        PostEntity postEntity = em.find(PostEntity.class, postId);
        em.remove(postEntity);
    }

    @Override
    public void updatePost(PostEditRequest request) {
        Long postId = request.getId();
        PostEntity postEntity = em.find(PostEntity.class,postId);
        if(postEntity == null)
            return;
        postEntity.setContent(request.getContent());
        postEntity.setTitle(request.getTitle());
    }

    @Override
    public void deleteAllPost() {
        em.createQuery("delete from PostEntity").executeUpdate();
    }

    @Override
    public List<PostEntity> findAllPost(PostSearchRequest request) {
        TypedQuery<PostEntity> query = em.createQuery("select p from PostEntity p where p.title LIKE CONCAT('%',:keyword,'%')",PostEntity.class);
        query.setParameter("keyword",request.getKeyword());
        return query.setMaxResults(request.getPageSize()).getResultList();
    }


    @Override
    public Long getTotalPostsSize(PostSearchRequest request) {
        return em.createQuery("select count(p) from PostEntity p",Long.class).getSingleResult();
    }
}
