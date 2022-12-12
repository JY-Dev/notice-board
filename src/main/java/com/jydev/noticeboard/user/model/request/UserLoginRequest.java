package com.jydev.noticeboard.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
