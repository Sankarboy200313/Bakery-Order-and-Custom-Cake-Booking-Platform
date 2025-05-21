package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.User;
import com.sesc.bakeryadmin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.readUsers();
    }

    @PostMapping
    public void addUser(@RequestBody Map<String, String> payload) {
        User user = new User(
            payload.get("username"), 
            "defaultPass", 
            payload.get("fullName"), 
            payload.get("email")
        );
        userService.addUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
}

