package com.pracaInzysnierka.pracainzynierska.unsecuredJwtTokens;

import lombok.Data;

@Data
class UserData {
    private String password;
    private String role;

    public UserData(String password, String role) {
        this.password = password;
        this.role = role;
    }

    public String getPassword() { return password; }
    public String getRole() { return role; }
}
