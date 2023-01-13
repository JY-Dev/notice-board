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
        // 인디케이터 리스트
        List<Long> list = new ArrayList<>();
        // 현재 검색조건에 해당하는 모든 게시글의 수를 조회
        Long totalPostsSize = postRepository.getTotalPostsSize(request);
        // 최대 페이지 넘버를 계산
        long maxPageNumber = calculatePageNumber(totalPostsSize/request.getPageSize(),totalPostsSize%request.getPageSize());
        // 현재 페이지의 글 갯수가 요청한 페이지의 갯수보다 작다면
        if(pagePostsSize < request.getPageSize()){
            // 최대 페이지 넘버까지 인디케이터 생성
            addPageIndicator(list,maxPageNumber);
        } else {
            // 현재 페이지 넘버
            int pageNum = request.getPageNum();
            // 남은 글 갯수
            long remainPostCnt = totalPostsSize - (long) pageNum * request.getPageSize();
            // 현재 페이지 기준으로 남은 페이지 갯수
            long remainPageCnt = calculatePageNumber(remainPostCnt/request.getPageSize(),remainPostCnt%request.getPageSize());
            // 인디케이터 중앙 index
            int center = indicatorSize / 2;
            //만약 현재페이지랑 남은 페이지 갯수의 합이 indicator 사이즈보다 크다면
            if(pageNum + remainPageCnt > indicatorSize){
                // 현재 페이지를 중앙으로 두었을 때를 기준으로 제일 오른쪽 페이지 넘버랑 최대 페이지넘버랑 비교해서 가장 작은 값으로 비교
                addPageIndicator(list,Math.max(Math.min(pageNum+center,maxPageNumber),indicatorSize));
            } else{
                addPageIndicator(list,pageNum+remainPageCnt);
            }
        }
        return list;
    }

    private long calculatePageNumber(long pageNumber, long remainPosts){
        // 남은 글이 있을 경우
        if(remainPosts > 0)
            // 페이지 넘버 1 증가
            return pageNumber+1;
        return pageNumber;
    }

    private void addPageIndicator(List<Long> list, long pageNum){
        int pageCnt = 0;
        // pageNum이 0보다 크면서 인디케이터 수 만큼 반복
        while(pageNum>0 && pageCnt < indicatorSize){
            list.add(0,pageNum);
            pageNum--;
            pageCnt++;
        }
    }
}
