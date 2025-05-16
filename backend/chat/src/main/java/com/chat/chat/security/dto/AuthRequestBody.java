package com.chat.chat.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequestBody {
    @NotNull
    @NotBlank(message = "Username is mandatory")
    String username;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    String password;
}
