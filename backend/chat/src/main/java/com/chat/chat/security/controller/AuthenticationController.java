package com.chat.chat.security.controller;

import com.chat.chat.security.User;
import com.chat.chat.security.dto.AuthRequestBody;
import com.chat.chat.security.dto.AuthResponseBody;
import com.chat.chat.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthService authService;

    @GetMapping("/users/me")
    public User getUser(@RequestAttribute("authenticatedUser") User user) {
        return user;
    }

    @PostMapping("/register")
    public AuthResponseBody registerPage(@Valid @RequestBody AuthRequestBody registerRequestBody) {
        return authService.register(registerRequestBody);
    }

    @PostMapping("/login")
    public AuthResponseBody loginPage(@Valid @RequestBody AuthRequestBody loginRequestBody) {
        return authService.login(loginRequestBody);
    }


}
