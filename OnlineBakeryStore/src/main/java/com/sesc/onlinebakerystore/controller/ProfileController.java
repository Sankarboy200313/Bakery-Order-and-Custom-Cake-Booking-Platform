package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.User;
import com.sesc.onlinebakerystore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

// @Controller indicates this class handles HTTP requests for profile-related functionality
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    // Displays the user profile page
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        // Retrieve the logged-in user's username from the session
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            // Redirect to login page if no user is logged in
            return "redirect:/login";
        }
        try {
            // Fetch user details using the username
            User user = userService.findByUsername(username);
            if (user != null) {
                // Add user details to the model for display in the template
                model.addAttribute("user", user);
                model.addAttribute("isLoggedIn", true); // Ensure navigation updates
                return "profile"; // Returns profile.html
            } else {
                // Add error message if user is not found
                model.addAttribute("error", "User not found");
                return "redirect:/login"; // Redirect to login page with error
            }
        } catch (Exception e) {
            // Handle any exceptions (e.g., file I/O errors)
            model.addAttribute("error", "Error retrieving profile: " + e.getMessage());
            return "redirect:/login"; // Redirect to login page with error
        }
    }

    // Handles account deletion
    @GetMapping("/delete-account")
    public String deleteAccount(HttpSession session) throws IOException {
        // Retrieve the logged-in user's username from the session
        String username = (String) session.getAttribute("loggedInUser");
        if (username != null) {
            // Remove the user from the file
            userService.deleteUser(username);
            // Invalidate the session
            session.invalidate();
        }
        // Redirect to the login page after deletion
        return "redirect:/login";
    }
}