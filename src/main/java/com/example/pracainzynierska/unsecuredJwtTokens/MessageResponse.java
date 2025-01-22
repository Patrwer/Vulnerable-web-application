package com.example.pracainzynierska.unsecuredJwtTokens;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MessageResponse {
    private String message;

    public MessageResponse() {}

    public MessageResponse(String message) {
        this.message = message;
    }
}