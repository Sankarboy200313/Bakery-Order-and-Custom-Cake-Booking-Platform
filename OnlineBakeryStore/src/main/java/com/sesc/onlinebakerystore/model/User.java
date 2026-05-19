package com.sesc.onlinebakerystore.model;

public class User {
    private String username;
    private String email;
    private String password;
    private String fullname;
    private String telephoneNo;
    private boolean admin;

    public User() {
    }

    public User(String username, String email, String password, String fullname, String telephoneNo) {
        this(username, email, password, fullname, telephoneNo, false);
    }

    public User(String username, String email, String password, String fullname, String telephoneNo, boolean admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.telephoneNo = telephoneNo;
        this.admin = admin;
    }

    public User(String username, String email, String password) {
        this(username, email, password, "", "", false);
    }

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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
