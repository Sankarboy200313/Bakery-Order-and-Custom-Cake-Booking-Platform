package com.sesc.onlinebakerystore.controller;

// Importing necessary classes for Spring and model handling
import com.sesc.onlinebakerystore.model.User;
import com.sesc.onlinebakerystore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Importing I/O for handling exceptions
import java.io.IOException;

// @Controller indicates this class handles HTTP requests for signup functionality
@Controller
public class SignupController {

    // @Autowired injects the UserService dependency for user operations
    @Autowired
    private UserService userService;

    // Display the signup page when accessing /signup
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        // Initialize isLoggedIn attribute to false to avoid null evaluation
        model.addAttribute("isLoggedIn", false);
        return "signup"; // Refers to signup.html in templates
    }

    // Handle signup form submission
    @PostMapping("/signup")
    public String signup(
            @RequestParam String username,              // Username from the form
            @RequestParam String password,              // Password from the form
            @RequestParam("confirm_password") String confirmPassword, // Confirm password from the form
            @RequestParam String fullname,              // Full name from the form
            @RequestParam String email,                 // Email from the form
            @RequestParam("telephon_no") String telephoneNo, // Telephone number from the form
            Model model                                 // Model to pass data to the view
    ) throws IOException {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            model.addAttribute("isLoggedIn", false);
            return "signup"; // Return to signup page with error
        }

        // Check if username already exists using UserService
        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("errorMessage", "Username already exists");
            model.addAttribute("isLoggedIn", false);
            return "signup"; // Return to signup page with error
        }

        // Create a new user with all fields and save it
        User user = new User(username, email, password, fullname, telephoneNo);
        userService.saveUser(user);
        // Redirect to login page after successful signup
        return "redirect:/login";
    }
}