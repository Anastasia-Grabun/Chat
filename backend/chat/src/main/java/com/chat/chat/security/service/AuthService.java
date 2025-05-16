package com.chat.chat.security.service;

import com.chat.chat.security.User;
import com.chat.chat.security.UserRepository;
import com.chat.chat.security.dto.AuthRequestBody;
import com.chat.chat.security.dto.AuthResponseBody;
import com.chat.chat.security.utils.Encoder;
import com.chat.chat.security.utils.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final Encoder encoder;
    private final JWT jwt;

    public User getUser(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public AuthResponseBody register(AuthRequestBody registerRequestBody) {
        userRepository.save(new User(registerRequestBody.getUsername(), registerRequestBody.getPassword()));
        String token = jwt.generateToken(registerRequestBody.getLogin());

        return new AuthResponseBody(token, "User register successfully");
    }

    public AuthResponseBody login(AuthRequestBody loginRequestBody) {
        User user = userRepository.findByLogin(loginRequestBody.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!encoder.matches(loginRequestBody.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect.");
        }
        String token = jwt.generateToken(loginRequestBody.getLogin());

        return new AuthResponseBody(token, "Authentication succeeded.");
    }
}
