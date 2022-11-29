package com.jydev.noticeboard.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @NotBlank
    private String nickname;

    @Email
    private String email;

    @NotBlank
    private String id;

    @NotBlank
    @Size(min = 8,max = 12)
    private String password;

    @NotBlank
    @Size(min = 8,max = 12)
    private String confirmPassword;
}
