package com.jydev.noticeboard.post.model.comment.entity;

import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "child_comment")
@EntityListeners(AuditingEntityListener.class)
public class ChildCommentEntity {

    @Id
    @Column(name = "child_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity parent;

    private String content;

    @CreatedDate
    private LocalDateTime createdDateTime;

    public void registerChildComment(CommentEntity parent){
        if(this.parent != null)
            this.parent.getChild().remove(this);
        parent.getChild().add(this);
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ChildCommentEntity other)
            return userEntity.equals(other.userEntity) && other.id.equals(other.getId());
        return false;
    }
}
