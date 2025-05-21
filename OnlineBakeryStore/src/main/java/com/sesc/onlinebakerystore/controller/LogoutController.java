package com.sesc.onlinebakerystore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controller indicates this class handles HTTP requests for logout functionality
@Controller
public class LogoutController {

    // Handles user logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session to clear all user data
        session.invalidate();
        // Redirect to the login page after logout
        return "redirect:/login";
    }
}