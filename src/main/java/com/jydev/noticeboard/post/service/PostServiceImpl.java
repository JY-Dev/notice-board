package com.jydev.noticeboard.post.service;

import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostMapper postMapper;
    @Override
    public Optional<Post> registerPost(PostRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId());
        if(userEntity == null)
            return Optional.empty();
        return Optional.of(postRepository.savePost(request,userEntity))
                .map(postMapper::toPost);
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deletePostById(postId);
    }

    @Override
    public Optional<Post> findPostById(Long postId) {
        return Optional.of(postRepository.findPostById(postId))
                .map(postMapper::toPost);
    }
}
