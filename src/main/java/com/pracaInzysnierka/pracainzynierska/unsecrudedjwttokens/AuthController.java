package com.pracaInzysnierka.pracainzynierska.unsecrudedjwttokens;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    private Map<String, String> users = new HashMap<>();

    public AuthController() {
        users.put("user", "password");
    }

    @GetMapping("/loginjwt")
    public String loginjwt() {
        return "loginjwt";
    }

    @PostMapping("/loginjwt")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {

        if (users.containsKey(username) && users.get(username).equals(password)) {
            String token = Jwts.builder()
                    .setSubject(username)
                    .signWith(SignatureAlgorithm.NONE, "")
                    .compact();

            model.addAttribute("token", token);
            return "user";
        }else {
            model.addAttribute("error", "Invalid username or password");
            return "loginjwt";
        }
    }
}
