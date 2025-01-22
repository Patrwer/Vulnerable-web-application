package com.example.pracainzynierska.unsecuredJwtTokens;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class AuthController {
    private static final String SECRET = "twojBardzoTajnyKluczMin32Znaki12345678";

    @Autowired
    private JwtUserRepository userRepository;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        JwtUser user = userRepository.findByUsername(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getRole())
                    .sign(algorithm);

            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Nieprawidłowe dane logowania"));
    }

    @GetMapping("/api/admin")
    public ResponseEntity<?> adminPanel(@RequestHeader("Authorization") String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token.replace("Bearer ", ""));

            String username = jwt.getSubject();
            return ResponseEntity.ok(new MessageResponse("Dostęp do panelu admina przyznany dla: " + username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Nieprawidłowy token"));
        }
    }

    @GetMapping("/unsecuredjwttokens")
    public String showJwtDemo() {
        return "unsecuredJwtTokens/unsecuredjwttokens";
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("Token received: " + token);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token.replace("Bearer ", ""));

            String role = jwt.getClaim("role").asString();
            System.out.println("Role from token: " + role);

            if ("ADMIN".equals(role)) {
                List<JwtUser> users = userRepository.findAll();
                System.out.println("Users found: " + users.size());
                return ResponseEntity.ok(users);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Access denied"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid token"));
        }
    }

    }

