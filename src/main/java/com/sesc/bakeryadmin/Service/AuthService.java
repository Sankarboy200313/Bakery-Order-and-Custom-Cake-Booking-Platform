package com.sesc.bakeryadmin.Service;

import com.sesc.bakeryadmin.Model.Admin;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // For now, use hardcoded credentials
    private final Admin admin = new Admin("A001", "admin", "admin123");

    public boolean validate(String username, String password) {
        return admin.getUsername().equals(username) && admin.getPassword().equals(password);
    }
} 