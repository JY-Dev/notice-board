package com.jydev.noticeboard.user;

import com.jydev.noticeboard.user.model.User;
import com.jydev.noticeboard.user.model.request.UserLoginRequest;
import com.jydev.noticeboard.user.model.request.UserRegisterRequest;
import com.jydev.noticeboard.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/login")
    public String loginUserForm(@ModelAttribute("userLoginForm") UserLoginRequest userLoginRequest){
        return "user/login";
    }

    @PostMapping("/login")
    public String userLogin(@Validated @ModelAttribute("userLoginForm") UserLoginRequest userLoginRequest, BindingResult bindingResult,
                            HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "user/login";
        }
        HttpSession session = request.getSession();
        Optional<User> userLogin = userService.login(session.getId(), userLoginRequest.getId(), userLoginRequest.getPassword());
        if(userLogin.isEmpty()){
            bindingResult.reject("invalid");
            return "user/login";
        }
        return "redirect:/";
    }
}
