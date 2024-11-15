package com.pracaInzysnierka.pracainzynierska.brokenauth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BrokenAuthController {

    private Map<String, String> users = new HashMap<>();

    public BrokenAuthController() {
        users.put("user", "password");
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            model.addAttribute("message", "welcome" + username);
            return "welcome.html";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login.html";
        }
    }




}
