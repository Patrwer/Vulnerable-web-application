package com.example.pracainzynierska.unsecuredJwtTokens;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginResponse {
    private String token;

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }
}