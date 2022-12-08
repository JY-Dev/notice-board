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
import com.jydev.noticeboard.user.util.UserDependency;

public class PostDependency {
    public static PostRepository postRepository = new MemoryPostRepositoryImpl(UserDependency.userMapper);
    public static PostMapper postMapper = new PostMapper();
    public static CommentMapper commentMapper = new CommentMapper();
    public static CommentRepository commentRepository = new MemoryCommentRepositoryImpl();
    public static CommentService commentService = new CommentServiceImpl(commentMapper,commentRepository,UserDependency.getUserRepository());
    public static PostService postService = new PostServiceImpl(postRepository,UserDependency.getUserRepository(),commentService,postMapper);
}
