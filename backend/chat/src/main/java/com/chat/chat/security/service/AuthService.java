package com.chat.chat.security.service;

import com.chat.chat.security.User;
import com.chat.chat.security.UserRepository;
import com.chat.chat.security.dto.AuthRequestBody;
import com.chat.chat.security.dto.AuthResponseBody;
import com.chat.chat.security.utils.Encoder;
import com.chat.chat.security.utils.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final Encoder encoder;
    private final JWT jwt;

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    @Transactional
    public AuthResponseBody register(AuthRequestBody registerRequestBody) {
        try {
            User user = new User(registerRequestBody.getUsername(), registerRequestBody.getPassword());
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String token = jwt.generateToken(registerRequestBody.getUsername());

        return new AuthResponseBody(token, "User register successfully");
    }

    @Transactional
    public AuthResponseBody login(AuthRequestBody loginRequestBody) {
        User user = userRepository.findByUsername(loginRequestBody.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!encoder.matches(loginRequestBody.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect.");
        }
        String token = jwt.generateToken(loginRequestBody.getUsername());

        return new AuthResponseBody(token, "Authentication succeeded.");
    }
}
