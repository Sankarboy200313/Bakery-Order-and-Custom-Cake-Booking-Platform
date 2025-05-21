package com.sesc.bakeryadmin.Model;

import lombok.Data;

@Data
public class Admin {
    private String adminId;
    private String username;
    private String password;

    public Admin() {}

    public Admin(String adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
