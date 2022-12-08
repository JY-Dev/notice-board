package com.jydev.noticeboard.post.service;

import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.post.service.comment.CommentService;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    private final PostMapper postMapper;
    @Override
    public Optional<Post> registerPost(PostRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId());
        if(userEntity == null)
            return Optional.empty();
        return Optional.ofNullable(postRepository.savePost(request,userEntity))
                .map(postEntity -> postMapper.toPost(postEntity,Collections.emptyList()));
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deletePostById(postId);
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        List<MappingCommentHierarchy> comments = commentService.getComments(postId);
        return Optional.ofNullable(postRepository.findPostById(postId))
                .map(postEntity -> postMapper.toPost(postEntity,comments));
    }
}
