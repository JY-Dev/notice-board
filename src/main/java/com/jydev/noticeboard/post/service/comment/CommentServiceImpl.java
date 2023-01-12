package com.jydev.noticeboard.post.service.comment;

import com.jydev.noticeboard.post.mapper.CommentMapper;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.post.repository.comment.CommentRepository;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Comment> registerComment(CommentRequest commentRequest) {
        UserEntity userEntity = userRepository.findById(commentRequest.getUserId());
        PostEntity postEntity = postRepository.findPostById(commentRequest.getPostId());
        if(userEntity == null || postEntity == null)
            return Optional.empty();
        CommentEntity parentComment = commentRepository.getCommentById(commentRequest.getParentId());
        return Optional.ofNullable(commentMapper.toComment(commentRepository.saveComment(commentRequest,parentComment,postEntity,userEntity)));
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    @Override
    public Optional<Comment> getComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.getCommentById(commentId);
        if(commentEntity == null)
            return Optional.empty();

        return Optional.of(commentMapper.toComment(commentEntity));
    }
}
