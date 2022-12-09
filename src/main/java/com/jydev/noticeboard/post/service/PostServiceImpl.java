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

import java.util.ArrayList;
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

    @Override
    public void updatePost(PostEditRequest request) {
        postRepository.updatePost(request);
    }

    @Override
    public List<PagePost> findPagePosts(PostSearchRequest request) {
        return postRepository.findAllPost(request).stream().map(postMapper::toPagePost).toList();
    }

    @Override
    public List<Integer> getPageIndicator(PostSearchRequest request, int pagePostsSize){
        List<Integer> list = new ArrayList<>();
        int totalPostsSize = postRepository.getTotalPostsSize();
        int maxPage = totalPostsSize/ request.getPageSize()-1;
        if(pagePostsSize < request.getPageSize()){
            int pageNum = request.getPageNum();
            if(pagePostsSize == 0)
                pageNum-=1;
            addPageIndicator(list,Math.min(pageNum,maxPage));

        } else {
            int remainPostCnt = totalPostsSize - request.getPageNum() * request.getPageSize();
            int pageNum = request.getPageNum();
            if(remainPostCnt == 0){
                addPageIndicator(list,pageNum);
            } else {
                int pageWeight = remainPostCnt/request.getPageSize();
                if(pageWeight == 0 && request.getPageNum() != 0)
                    pageWeight = 1;
                int center = indicatorSize / 2;
                if(center < pageWeight)
                    addPageIndicator(list,Math.max(pageNum+center,indicatorSize-1));
                else
                    addPageIndicator(list,pageNum+pageWeight);
            }
        }
        return list;
    }

    private void addPageIndicator(List<Integer> list, int pageNum){
        int pageCnt = 0;
        while(pageNum>=0 && pageCnt < indicatorSize){
            list.add(0,pageNum+1);
            pageNum--;
            pageCnt++;
        }
    }
}
