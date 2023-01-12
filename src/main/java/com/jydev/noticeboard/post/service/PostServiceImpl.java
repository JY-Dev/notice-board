package com.jydev.noticeboard.post.service;

import com.jydev.noticeboard.post.mapper.PostMapper;
import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.comment.MappingCommentHierarchy;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.model.request.PostSearchRequest;
import com.jydev.noticeboard.post.repository.PostRepository;
import com.jydev.noticeboard.post.service.comment.CommentService;
import com.jydev.noticeboard.user.model.entity.UserEntity;
import com.jydev.noticeboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
                .map(postMapper::toPost);
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deletePostById(postId);
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        return Optional.ofNullable(postRepository.findPostById(postId))
                .map(postMapper::toPost);
    }

    @Override
    public void updatePost(PostEditRequest request) {
        postRepository.updatePost(request);
    }

    @Override
    public List<PagePost> findPagePosts(PostSearchRequest request) {
        return postRepository.findAllPost(request).stream().map(postMapper::toPagePost).toList();
    }

    @Override
    public List<Long> getPageIndicator(PostSearchRequest request, int pagePostsSize){
        List<Long> list = new ArrayList<>();
        Long totalPostsSize = postRepository.getTotalPostsSize(request);
        long maxPageNumber = calculatePageNumber(totalPostsSize/request.getPageSize(),totalPostsSize%request.getPageSize());
        if(pagePostsSize < request.getPageSize()){
            addPageIndicator(list,maxPageNumber);
        } else {
            int pageNum = request.getPageNum();
            long remainPostCnt = totalPostsSize - (long) pageNum * request.getPageSize();
            long pageWeight = calculatePageNumber(remainPostCnt/request.getPageSize(),remainPostCnt%request.getPageSize());
            int center = indicatorSize / 2;
            if(pageNum + pageWeight > indicatorSize)
                addPageIndicator(list,Math.max(Math.min(pageNum+center,maxPageNumber),indicatorSize));
            else
                addPageIndicator(list,pageNum+pageWeight);
        }
        return list;
    }

    private long calculatePageNumber(long pageNumber, long remainPosts){
        if(remainPosts > 0)
            return pageNumber+1;
        return pageNumber;
    }

    private void addPageIndicator(List<Long> list, long pageNum){
        int pageCnt = 0;
        while(pageNum>0 && pageCnt < indicatorSize){
            list.add(0,pageNum);
            pageNum--;
            pageCnt++;
        }
    }
}
