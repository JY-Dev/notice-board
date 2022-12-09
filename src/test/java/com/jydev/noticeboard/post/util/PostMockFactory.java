package com.jydev.noticeboard.post.util;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.PostUser;
import com.jydev.noticeboard.post.model.entity.PostEntity;
import com.jydev.noticeboard.post.model.request.PostEditRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.user.util.UserData;
import com.jydev.noticeboard.user.util.UserMockFactory;

import java.time.LocalDateTime;
import java.util.Collections;

public class PostMockFactory {


    public static Post makePost(){
        return new Post(PostData.postId,makePostUser(),"","", LocalDateTime.now(),0, Collections.emptyList());
    }

    public static PostEntity makePostEntity(){
        return new PostEntity(PostData.postId,"","",LocalDateTime.now(), UserMockFactory.makeUserEntity());
    }

    public static PostEditRequest makePostEditRequest(){
        return new PostEditRequest(PostData.postId,PostData.changeTitle,PostData.changeContent);
    }

    public static PostRequest makePostRequest(){
        return new PostRequest("","", UserData.userId);
    }

    public static PostUser makePostUser(){
        return new PostUser(UserData.profileImageUrl,UserData.nickname,UserData.userId);
    }
}
