package com.jydev.noticeboard.post.util;

import com.jydev.noticeboard.post.mapper.CommentMapper;
import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.repository.MemoryPostRepositoryImpl;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.post.repository.comment.CommentRepository;
import com.jydev.noticeboard.post.repository.comment.MemoryCommentRepositoryImpl;
import com.jydev.noticeboard.post.service.PostService;
import com.jydev.noticeboard.post.service.PostServiceImpl;
import com.jydev.noticeboard.post.service.comment.CommentService;
import com.jydev.noticeboard.post.service.comment.CommentServiceImpl;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.util.UserDependency;

public class PostDependency {
    public static CommentMapper commentMapper = new CommentMapper();
    public static PostMapper postMapper = new PostMapper(commentMapper);

    public static final UserRepository userRepository = UserDependency.getUserRepository();
    public static final PostRepository postRepository = new MemoryPostRepositoryImpl(UserDependency.userMapper);

    public static CommentRepository getCommentRepository(){
        return new MemoryCommentRepositoryImpl();
    }
    public static CommentService getCommentService(){
        return new CommentServiceImpl(commentMapper,getCommentRepository(),postRepository,userRepository);
    }

    public static PostService getPostService(){
        return new PostServiceImpl(postRepository,userRepository,getCommentService(),postMapper);
    }
}
