package com.sesc.bakeryadmin.Service;

import com.sesc.bakeryadmin.Model.Admin;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private static final String ADMINS_FILE_PATH = "src/main/resources/data/admin.txt";

    public List<Admin> getAllAdmins() {
        return readAdminsFromFile();
    }

    public Admin addAdmin(Admin admin) {
        validateAdmin(admin);
        List<Admin> admins = getAllAdmins();
        
        // Check if adminId already exists
        if (admins.stream().anyMatch(a -> a.getAdminId().equals(admin.getAdminId()))) {
            throw new IllegalArgumentException("Admin ID already exists");
        }
        
        // Check if username already exists
        if (admins.stream().anyMatch(a -> a.getUsername().equals(admin.getUsername()))) {
            throw new IllegalArgumentException("Admin username already exists");
        }
        
        admins.add(admin);
        writeAdminsToFile(admins);
        return admin;
    }

    public void deleteAdmin(String adminId) {
        List<Admin> admins = getAllAdmins();
        if (admins.stream().noneMatch(a -> a.getAdminId().equals(adminId))) {
            throw new IllegalArgumentException("Admin not found");
        }
        
        admins = admins.stream()
                .filter(a -> !a.getAdminId().equals(adminId))
                .collect(Collectors.toList());
        writeAdminsToFile(admins);
    }

    public Admin updateAdmin(String adminId, String username, String password) {
        validateUpdateFields(username, password);
        
        List<Admin> admins = getAllAdmins();
        if (admins.stream().noneMatch(a -> a.getAdminId().equals(adminId))) {
            throw new IllegalArgumentException("Admin not found");
        }
        
        // Check if new username already exists (excluding current admin)
        if (admins.stream()
                .filter(a -> !a.getAdminId().equals(adminId))
                .anyMatch(a -> a.getUsername().equals(username))) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        Admin updatedAdmin = new Admin(adminId, username, password);
        admins = admins.stream()
                .map(a -> a.getAdminId().equals(adminId) ? updatedAdmin : a)
                .collect(Collectors.toList());
        writeAdminsToFile(admins);
        return updatedAdmin;
    }

    private List<Admin> readAdminsFromFile() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ADMINS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    admins.add(new Admin(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            try {
                new File(ADMINS_FILE_PATH).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return admins;
    }

    private void writeAdminsToFile(List<Admin> admins) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMINS_FILE_PATH))) {
            for (Admin admin : admins) {
                bw.write(String.format("%s,%s,%s%n",
                    admin.getAdminId(),
                    admin.getUsername(),
                    admin.getPassword()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateAdmin(Admin admin) {
        if (admin.getAdminId() == null || admin.getAdminId().trim().isEmpty()) {
            throw new IllegalArgumentException("Admin ID cannot be empty");
        }
        if (admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

    private void validateUpdateFields(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
} 