package com.chat.chat.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequestBody {
    @NotBlank(message = "Username is mandatory")
    String username;

    @NotBlank(message = "Login is mandatory")
    String login;

    @NotBlank(message = "Password is mandatory")
    String password;
}
