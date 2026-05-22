package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.User;
import com.sesc.onlinebakerystore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        return "signup"; // Refers to signup.html in templates
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam("confirm_password") String confirmPassword,
            @RequestParam String fullname,
            @RequestParam String email,
            @RequestParam("telephon_no") String telephoneNo,
            Model model
    ) throws IOException {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "signup"; // Return to signup page with error
        }

        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("errorMessage", "Username already exists");
            return "signup"; // Return to signup page with error
        }

        User user = new User(username, email, password, fullname, telephoneNo);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
