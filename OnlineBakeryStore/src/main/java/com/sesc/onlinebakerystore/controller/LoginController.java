package com.sesc.onlinebakerystore.controller;

// Importing necessary classes for Spring and model handling
import com.sesc.onlinebakerystore.model.User;
import com.sesc.onlinebakerystore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Importing I/O for handling exceptions
import java.io.IOException;

// @Controller indicates this class handles HTTP requests for login functionality
@Controller
public class LoginController {

    // @Autowired injects the UserService dependency for user authentication
    @Autowired
    private UserService userService;

    // Displays the login form when accessing /login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // Clear any existing session to ensure fresh login
        model.addAttribute("isLoggedIn", false);
        return "login"; // Refers to login.html in templates
    }

    // Handles login form submission
    @PostMapping("/login")
    public String login(
            @RequestParam String username,           // Username from the form
            @RequestParam String password,           // Password from the form
            HttpSession session,                    // Session to store logged-in user
            Model model                             // Model to pass error messages to the view
    ) {
        try {
            // Authenticate the user using the provided credentials
            User user = userService.authenticate(username, password);
            if (user != null) {
                // Store the username in the session to track the logged-in user
                session.setAttribute("loggedInUser", user.getUsername());
                session.setAttribute("isLoggedIn", true); // Set login status
                // Redirect to the profile page on successful login
                return "redirect:/profile";
            } else {
                // Add error message if credentials are invalid
                model.addAttribute("error", "Invalid username or password");
                model.addAttribute("isLoggedIn", false);
                return "login"; // Return to login page with error
            }
        } catch (IOException e) {
            // Handle any I/O exceptions (e.g., file reading errors)
            model.addAttribute("error", "An error occurred during login: " + e.getMessage());
            model.addAttribute("isLoggedIn", false);
            return "login"; // Return to login page with error
        }
    }
}