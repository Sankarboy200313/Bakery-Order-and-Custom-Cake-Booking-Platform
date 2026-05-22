package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.User;
import com.sesc.onlinebakerystore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // Refers to login.html in templates
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        try {
            User user = userService.authenticate(username, password);
            if (user != null) {
                session.setAttribute("loggedInUser", user.getUsername());
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("isAdmin", user.isAdmin());
                return user.isAdmin() ? "redirect:/admin-dashboard" : "redirect:/profile";
            } else {
                model.addAttribute("errorMessage", "Invalid username or password");
                return "login"; // Return to login page with error
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", "An error occurred during login: " + e.getMessage());
            return "login"; // Return to login page with error
        }
    }
}
