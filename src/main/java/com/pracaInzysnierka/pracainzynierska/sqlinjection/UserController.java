package com.pracaInzysnierka.pracainzynierska.sqlinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/users")
    public List<User> getUsersByUsername(@RequestParam String username) {
        // Zapytanie SQL podatne na SQL Injection
        String sql = "SELECT * FROM USER WHERE username = '" + username + "'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }
}
