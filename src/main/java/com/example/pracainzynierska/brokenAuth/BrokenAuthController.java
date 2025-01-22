package com.example.pracainzynierska.brokenAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;


@Controller
public class BrokenAuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/brokenlogin")
    public String loginPage() {
        return "brokenauth/brokenlogin";
    }

    @PostMapping("/brokenlogin")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("Login attempt - username: " + username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        System.out.println("User found: " + optionalUser.isPresent());

        if (optionalUser.isPresent()) {
            System.out.println("Password match: " + optionalUser.get().getPassword().equals(password));
            if (optionalUser.get().getPassword().equals(password)) {
                Cookie cookie = new Cookie("SESSIONID", "12345");
                cookie.setPath("/");
                cookie.setHttpOnly(false);
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
                return "redirect:brokenauth/welcome";
            }
        }

        model.addAttribute("error", "Invalid username or password");
        return "brokenauth/brokenlogin";
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        boolean sessionValid = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName()) && "12345".equals(cookie.getValue())) {
                    sessionValid = true;
                    break;
                }
            }
        }

        if (sessionValid) {
            model.addAttribute("message", "Welcome back, admin!");
            return "brokenauth/welcome";
        } else {
            return "redirect:brokenauth/brokenlogin";
        }
    }
}