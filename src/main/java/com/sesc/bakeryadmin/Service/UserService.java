package com.sesc.bakeryadmin.Service;

import com.sesc.bakeryadmin.Model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final String USERS_FILE_PATH = "src/main/resources/data/users.txt";

    public List<User> readUsers() {
        return readFromFile(USERS_FILE_PATH);
    }

    private List<User> readFromFile(String filePath) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            // Create file if it doesn't exist
            try {
                new File(filePath).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return users;
    }

    private void writeToFile(List<User> users, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User u : users) {
                bw.write(String.format("%s,%s,%s,%s%n", 
                    u.getUsername(), u.getPassword(), u.getFullName(), u.getEmail()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        List<User> users = readUsers();
        users.add(user);
        writeToFile(users, USERS_FILE_PATH);
    }

    public void deleteUser(String userId) {
        List<User> users = readUsers().stream()
                .filter(u -> !u.getUsername().equals(userId))
                .collect(Collectors.toList());
        writeToFile(users, USERS_FILE_PATH);
    }
} 