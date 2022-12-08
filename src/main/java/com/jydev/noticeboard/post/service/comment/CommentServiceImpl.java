package com.jydev.noticeboard.post.service.comment;

import com.jydev.noticeboard.post.mapper.CommentMapper;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.repository.comment.CommentRepository;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Comment> registerComment(CommentRequest commentRequest) {
        UserEntity userEntity = userRepository.findById(commentRequest.getUserId());
        if(userEntity == null)
            return Optional.empty();

        return Optional.ofNullable(commentMapper.toComment(commentRepository.saveComment(commentRequest,userEntity)));
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    @Override
    public List<MappingCommentHierarchy> getComments(Long postId) {
        List<CommentEntity> commentEntities = commentRepository.findCommentsByPostId(postId);
        return commentEntities.stream()
                .filter(commentEntity -> commentEntity.getParentId() == -1)
                .map(commentMapper::toComment)
                .map(parentComment -> commentMapper.toMappingCommentHierarchy(commentEntities,parentComment)).toList();
    }
}
