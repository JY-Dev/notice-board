package com.jydev.noticeboard.post.mapper;

import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toComment(CommentEntity commentEntity){
        return new Comment(commentEntity.getId(),commentEntity.getParentId(),
                commentEntity.getContent(),commentEntity.getCreateDateTime());
    }
}
