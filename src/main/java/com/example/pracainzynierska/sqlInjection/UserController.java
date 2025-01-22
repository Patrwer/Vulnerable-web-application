package com.example.pracainzynierska.sqlInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Wyświetlenie formularza logowania
    @GetMapping("/sqlinjection")
    public String showLoginForm() {
        return "sqlIn/sqlinjection";
    }

    //  Logowanie
    @PostMapping("/sqlinjection")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Poniższe zapytanie jest podatne na SQL Injection
        String sql = "SELECT * FROM USER WHERE username = '" + username + "' AND password = '" + password + "'";

        return jdbcTemplate.query(sql, (rs) -> {
            if (rs.next()) {
                model.addAttribute("username", rs.getString("username"));
                return "sqlIn/loggedin";
            } else {
                model.addAttribute("error", "Niepoprawny login lub hasło!");
                return "sqlIn/sqlinjection";
            }
        });
    }

    @GetMapping("/loggedin")
    public String welcomeUser(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "sqlIn/loggedin";
    }
}
