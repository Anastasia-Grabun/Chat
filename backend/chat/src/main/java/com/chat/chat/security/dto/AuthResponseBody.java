package com.chat.chat.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseBody {

    String token;
    String message;

}
