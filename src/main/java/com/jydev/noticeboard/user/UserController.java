package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/register")
    public String registerUserForm(@ModelAttribute("userRegisterForm") UserRegisterRequest userRegisterRequest){
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@Validated @ModelAttribute("userRegisterForm") UserRegisterRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "user/register";
        if(!request.getPassword().equals(request.getConfirmPassword())){
            bindingResult.rejectValue("password","inCollect");
            return "user/register";
        }
        if(userService.registerUser(request).isEmpty()){
            bindingResult.rejectValue("id","conflict");
            return "user/register";
        }


        return "redirect:login";
    }
}
