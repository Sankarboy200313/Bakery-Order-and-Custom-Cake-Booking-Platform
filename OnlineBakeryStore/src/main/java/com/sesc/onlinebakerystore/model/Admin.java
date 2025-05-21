package com.sesc.onlinebakerystore.model;

import java.util.ArrayList;
import java.util.List;

// Class representing an Admin entity, extending User
public class Admin extends User {
    private List<String> permissions; // Admin-specific permissions (e.g., "MANAGE_USERS", "VIEW_REPORTS")

    // Default constructor
    public Admin() {
        super(); // Call User default constructor
        this.permissions = new ArrayList<>();
        setAdmin(true); // Ensure isAdmin is true
    }

    // Constructor with all fields
    public Admin(String username, String email, String password, String fullname, String telephoneNo, List<String> permissions) {
        super(username, email, password, fullname, telephoneNo, true); // Set isAdmin to true
        this.permissions = permissions != null ? new ArrayList<>(permissions) : new ArrayList<>();
    }

    // Constructor for backward compatibility
    public Admin(String username, String email, String password) {
        super(username, email, password);
        this.permissions = new ArrayList<>();
        setAdmin(true); // Ensure isAdmin is true
    }

    // Getters and setters
    public List<String> getPermissions() {
        return new ArrayList<>(permissions); // Return a copy to protect encapsulation
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = new ArrayList<>(permissions);
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
    }
}