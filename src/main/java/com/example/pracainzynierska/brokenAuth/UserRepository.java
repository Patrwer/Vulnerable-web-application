package com.example.pracainzynierska.brokenAuth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(query, new Object[]{username}, userRowMapper());
            System.out.println("Found user: " + (user != null ? user.getUsername() : "null"));
            return Optional.ofNullable(user);
        } catch (Exception e) {
            System.out.println("Error finding user: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void saveUser(User user) {
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        jdbcTemplate.update(query, user.getUsername(), user.getPassword());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(rs.getString("username"), rs.getString("password"));
    }
}
