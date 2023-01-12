package com.jydev.noticeboard.post.service.comment;

import com.jydev.noticeboard.post.mapper.CommentMapper;
import com.jydev.noticeboard.post.model.comment.Comment;
import com.jydev.noticeboard.post.model.comment.entity.CommentEntity;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.post.repository.comment.CommentRepository;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
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
        if(isChildComment(commentRequest.getParentId())){
            CommentEntity parentComment = commentRepository.getCommentById(commentRequest.getParentId());
            return Optional.ofNullable(commentMapper.toComment(commentRepository.saveChildComment(commentRequest,parentComment,postEntity,userEntity)));
        } else
            return Optional.ofNullable(commentMapper.toComment(commentRepository.saveComment(commentRequest,postEntity,userEntity)));
    }

    @Override
    public void deleteComment(Long parentId, Long commentId) {
        if(isChildComment(parentId))
            commentRepository.deleteChildCommentById(commentId);
        else
            commentRepository.deleteCommentById(commentId);
    }

    @Override
    public Optional<Comment> getComment(Long parentId, Long commentId) {
        if(isChildComment(parentId))
            return Optional.ofNullable(commentMapper.toComment(commentRepository.getChildCommentById(commentId)));
        else
            return Optional.ofNullable(commentMapper.toComment(commentRepository.getCommentById(commentId)));
    }

    private boolean isChildComment(long parentId){
        return parentId != -1L;
    }
}
