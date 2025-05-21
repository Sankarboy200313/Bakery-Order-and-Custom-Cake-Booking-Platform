package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.User;
import com.sesc.bakeryadmin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.readUsers();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody Map<String, String> payload) {
        User user = new User(payload.get("username"), "defaultPass", payload.get("fullName"), payload.get("email"));
        userService.addUser(user);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/admins")
    public void addAdmin(@RequestBody Map<String, String> payload) {
        User admin = new User(payload.get("username"), payload.get("password"), "Admin", payload.get("username") + "@bakery.com");
        userService.addUser(admin);
    }

    @DeleteMapping("/admins/{adminId}")
    public void deleteAdmin(@PathVariable String adminId) {
        userService.deleteUser(adminId);
    }

    @PutMapping("/admins/{adminId}")
    public void updateAdmin(@PathVariable String adminId, @RequestBody Map<String, String> payload) {
        userService.updateAdmin(adminId, payload.get("username"), payload.get("password"));
    }
}

