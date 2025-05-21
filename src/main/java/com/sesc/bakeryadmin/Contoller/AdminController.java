package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.Admin;
import com.sesc.bakeryadmin.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody Map<String, String> payload) {
        Admin admin = new Admin(
            payload.get("adminId"),
            payload.get("username"),
            payload.get("password")
        );
        return ResponseEntity.ok(adminService.addAdmin(admin));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable String adminId,
            @RequestBody Map<String, String> payload) {
        Admin updatedAdmin = adminService.updateAdmin(
            adminId,
            payload.get("username"),
            payload.get("password")
        );
        return ResponseEntity.ok(updatedAdmin);
    }
} 