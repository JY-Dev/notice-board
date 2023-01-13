package com.jydev.noticeboard.post.util;

import com.jydev.noticeboard.post.model.PagePost;
import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.PostUser;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.model.request.PostSearchRequest;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserMockFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostMockFactory {


    public static Post makePost(){
        return new Post(PostData.postId,makePostUser(),PostData.title,"", LocalDateTime.now(),0, Collections.emptyList());
    }

    public static Post makePost(Long postId){
        return new Post(postId,makePostUser(),PostData.title,"", LocalDateTime.now(),0, Collections.emptyList());
    }

    public static PostEntity makePostEntity(Long postId){
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postId);
        postEntity.setTitle(PostData.title);
        postEntity.setContent("");
        postEntity.setCreatedDateTime(LocalDateTime.now());
        postEntity.setUser(UserMockFactory.makeUserEntity());
        return postEntity;
    }

    public static PostEditRequest makePostEditRequest(Long postId){
        return new PostEditRequest(postId,PostData.changeTitle,PostData.changeContent);
    }

    public static PostRequest makePostRequest(){
        return new PostRequest(PostData.title,"", UserData.userId);
    }

    public static PostRequest makePostEmptyTitleRequest(){
        return new PostRequest("","", UserData.userId);
    }

    public static PostUser makePostUser(){
        return new PostUser(UserData.profileImageUrl,UserData.nickname,UserData.userId);
    }

    public static List<PagePost> makePagePosts(){
        List<PagePost> pagePosts = new ArrayList<>();
        for (int i = 0; i < PostData.PAGE_POSTS_MAX_SIZE; i++) {
            pagePosts.add(new PagePost((long) i,makePostUser(),PostData.title,"",LocalDateTime.now()));
        }
        return pagePosts;
    }

    public static PostSearchRequest makePostAllSearchRequest(){
        return new PostSearchRequest("",0,PostData.PAGE_POSTS_MAX_SIZE);
    }

    public static PostSearchRequest makePostSearchRequest(Integer pageNum, Integer pageSize){
        return new PostSearchRequest("",pageNum,pageSize);
    }

    public static PostSearchRequest makePostAllSearchRequest(int pageNum){
        return new PostSearchRequest("",pageNum,PostData.pageSize);
    }

    public static PostSearchRequest makePostKeywordSearchRequest(){
        return new PostSearchRequest(PostData.keyword,0,PostData.PAGE_POSTS_MAX_SIZE);
    }

    public static PostSearchRequest makePostLackSearchRequest(){
        return new PostSearchRequest(PostData.keyword,15,PostData.PAGE_POSTS_MAX_SIZE);
    }
}
