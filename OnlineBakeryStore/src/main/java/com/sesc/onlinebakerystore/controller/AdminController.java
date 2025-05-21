package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.Admin;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }
        try {
            User user = userService.findByUsername(username);
            if (user != null && user.isAdmin()) {
                List<User> users = userService.readUsers();
                if (users == null) users = new ArrayList<>();
                model.addAttribute("users", users);
                model.addAttribute("isLoggedIn", true);
                model.addAttribute("isAdmin", true);
                if (users.isEmpty()) {
                    model.addAttribute("error", "No users found in the system.");
                }
                return "admin-dashboard";
            } else {
                return "redirect:/login";
            }
        } catch (IOException e) {
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
            model.addAttribute("users", new ArrayList<>());
            return "admin-dashboard";
        }
    }

    @GetMapping("/admin/edit-user")
    public String showEditUserForm(@RequestParam String username, HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                User userToEdit = userService.findByUsername(username);
                if (userToEdit != null) {
                    model.addAttribute("user", userToEdit);
                    model.addAttribute("isLoggedIn", true);
                    model.addAttribute("isAdmin", true);
                    return "edit-user";
                } else {
                    model.addAttribute("error", "User not found");
                    return "redirect:/admin-dashboard";
                }
            } else {
                return "redirect:/login";
            }
        } catch (IOException e) {
            model.addAttribute("error", "Error retrieving user: " + e.getMessage());
            return "redirect:/admin-dashboard";
        }
    }

    @PostMapping("/admin/update-user")
    public String updateUser(
            @RequestParam String oldUsername,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String fullname,
            @RequestParam String telephoneNo,
            @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin,
            HttpSession session,
            Model model
    ) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                User updatedUser = isAdmin ? new Admin(username, email, password, fullname, telephoneNo, List.of("MANAGE_USERS"))
                        : new User(username, email, password, fullname, telephoneNo, isAdmin);
                userService.updateUser(oldUsername, updatedUser);
                return "redirect:/admin-dashboard";
            } else {
                return "redirect:/login";
            }
        } catch (IOException e) {
            model.addAttribute("error", "Error updating user: " + e.getMessage());
            return "edit-user";
        }
    }
}