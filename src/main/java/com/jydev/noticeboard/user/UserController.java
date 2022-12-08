package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.LoginStatus;
import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserLoginRequest;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
@Slf4j
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

    @GetMapping("/login")
    public String loginUserForm(@ModelAttribute("userLoginForm") UserLoginRequest userLoginRequest){
        return "user/login";
    }

    @PostMapping("/login")
    public String userLogin(@Validated @ModelAttribute("userLoginForm") UserLoginRequest userLoginRequest, BindingResult bindingResult,
                            HttpServletRequest request, @RequestParam("redirectURL") String redirectUrl){
        if(bindingResult.hasErrors()){
            return "user/login";
        }
        HttpSession session = request.getSession();
        LoginStatus loginStatus = userService.login(session.getId(), userLoginRequest.getId(), userLoginRequest.getPassword());
        if(!loginStatus.equals(LoginStatus.SUCCESS)){
            String errorCode = "invalid";
            if(loginStatus.equals(LoginStatus.CONCURRENCY_MAX))
                errorCode = "concurrency";
            bindingResult.reject(errorCode);
            return "user/login";
        }
        String returnUrl = "redirect:";
        if(!redirectUrl.isEmpty()){
            returnUrl+=redirectUrl;
        } else
            returnUrl+="/";
        return returnUrl;
    }
}
