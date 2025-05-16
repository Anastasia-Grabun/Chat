package com.chat.chat.configuration;

import com.chat.chat.security.User;
import com.chat.chat.security.UserRepository;
import com.chat.chat.security.utils.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoadDatabaseConfiguration {
    private final Encoder encoder;

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            User user = new User("test_login", encoder.encode("password1"));
            User user2 = new User("test_login2", encoder.encode("password2"));
            userRepository.save(user);
            userRepository.save(user2);
        };
    }

}
