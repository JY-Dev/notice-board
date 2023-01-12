package com.jydev.noticeboard.post.comment.util;

import com.jydev.noticeboard.post.mapper.CommentMapper;
import com.jydev.noticeboard.post.repository.comment.CommentRepository;
import com.jydev.noticeboard.post.repository.comment.MemoryCommentRepositoryImpl;
import com.jydev.noticeboard.post.service.comment.CommentService;
import com.jydev.noticeboard.post.service.comment.CommentServiceImpl;
import com.jydev.noticeboard.post.util.PostDependency;
import com.jydev.noticeboard.user.repository.UserRepository;
import com.jydev.noticeboard.user.util.UserDependency;

public class CommentDependency {
    public static CommentMapper commentMapper = new CommentMapper();
    public static final UserRepository userRepository = UserDependency.getUserRepository();
    public static CommentRepository getCommentRepository(){
        return new MemoryCommentRepositoryImpl();
    }
    public static CommentService getCommentService(){
        return new CommentServiceImpl(commentMapper,getCommentRepository(), PostDependency.postRepository, userRepository);
    }
}
