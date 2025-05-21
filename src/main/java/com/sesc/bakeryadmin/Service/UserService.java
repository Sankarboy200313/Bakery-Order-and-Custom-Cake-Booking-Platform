package com.sesc.bakeryadmin.Service;

import com.sesc.bakeryadmin.Model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final String FILE_PATH = "users.txt";

    public List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void writeUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                bw.write(String.format("%s,%s,%s,%s%n", u.getUsername(), u.getPassword(), u.getFullName(), u.getEmail()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        List<User> users = readUsers();
        users.add(user);
        writeUsers(users);
    }

    public void deleteUser(String userId) {
        List<User> users = readUsers().stream()
                .filter(u -> !u.getUsername().equals(userId))
                .collect(Collectors.toList());
        writeUsers(users);
    }

    public void updateAdmin(String userId, String username, String password) {
        List<User> users = readUsers().stream()
                .map(u -> u.getUsername().equals(userId) ? new User(username, password, u.getFullName(), u.getEmail()) : u)
                .collect(Collectors.toList());
        writeUsers(users);
    }
}

