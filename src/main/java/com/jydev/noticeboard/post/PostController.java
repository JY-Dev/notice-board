package com.jydev.noticeboard.post;

import com.jydev.noticeboard.post.model.Post;
import com.jydev.noticeboard.post.model.comment.request.CommentRequest;
import com.jydev.noticeboard.post.model.request.PostRequest;
import com.jydev.noticeboard.post.service.PostService;
import com.jydev.noticeboard.user.AttributeLoginUser;
import com.jydev.noticeboard.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/page")
    public String getPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        return "index";
    }

    @GetMapping
    public String post(@ModelAttribute("postRequest") PostRequest postRequest) {

        return "post/postForm";
    }

    @PostMapping
    public String registerPost(@Validated @ModelAttribute("postRequest") PostRequest postRequest,@AttributeLoginUser User user,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "post/postForm";
        }
        postRequest.setUserId(user.getId());
        Optional<Post> post = postService.registerPost(postRequest);
        if(post.isEmpty()){
            redirectAttributes.addAttribute("postRequest",postRequest);
            return "redirect:/post";
        }
        return "redirect:/post/"+post.get().getId();
    }

    @GetMapping("/{postNumber}")
    public String postInfo(@PathVariable Long postNumber, Model model) {
        Optional<Post> post = postService.getPost(postNumber);
        if(post.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("post",post.get());
        return "post/postInfo";
    }

    @ResponseBody
    @PostMapping("/{postNumber}/comment")
    public void registerComment(@PathVariable Long postNumber, @RequestBody CommentRequest comment,
                                Model model, @AttributeLoginUser User user){

    }


}
