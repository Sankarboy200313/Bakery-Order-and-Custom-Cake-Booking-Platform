package com.sesc.bakeryadmin.Model;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;

    public User(String username, String password, String admin, String username1) {
        this.username = username;
        this.password = password;
        this.fullName = admin;
        this.email = username1;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // Getters, setters, constructors
}
