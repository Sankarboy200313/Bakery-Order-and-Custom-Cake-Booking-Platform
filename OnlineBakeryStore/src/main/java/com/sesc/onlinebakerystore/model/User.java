package com.sesc.onlinebakerystore.model;

// Class representing a User entity
public class User {
    private String username;
    private String email;
    private String password;
    private String fullname;
    private String telephoneNo;

    // Default constructor
    public User() {}

    // Constructor with all fields
    public User(String username, String email, String password, String fullname, String telephoneNo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.telephoneNo = telephoneNo;
    }

    // Constructor for reading from file (without fullname and telephoneNo for backward compatibility)
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }
}