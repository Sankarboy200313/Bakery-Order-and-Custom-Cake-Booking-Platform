package com.sesc.onlinebakerystore.model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<String> permissions;

    public Admin() {
        super();
        this.permissions = new ArrayList<>();
        setAdmin(true);
    }

    public Admin(String username, String email, String password, String fullname, String telephoneNo, List<String> permissions) {
        super(username, email, password, fullname, telephoneNo, true);
        this.permissions = permissions != null ? new ArrayList<>(permissions) : new ArrayList<>();
    }

    public Admin(String username, String email, String password) {
        super(username, email, password, "", "", true);
        this.permissions = new ArrayList<>();
    }

    public List<String> getPermissions() {
        return new ArrayList<>(permissions);
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = new ArrayList<>(permissions);
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
    }
}
