package com.example.pracainzynierska.unsecuredJwtTokens;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorResponse {
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String message) {
        this.message = message;
    }
}
