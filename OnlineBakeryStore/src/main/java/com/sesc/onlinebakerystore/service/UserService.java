package com.sesc.onlinebakerystore.service;

import com.sesc.onlinebakerystore.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    // File path for storing user data
    private static final String FILE_PATH = "src/main/resources/Profile_Details.txt";

    // Saves a new user to the Profile_Details.txt file in the requested format
    public void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Write user details in the format: Username: [value], Email: [value], Password: [value], Full Name: [value], Telephone: [value]
            writer.write("Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Password: " + user.getPassword() +
                    ", Full Name: " + (user.getFullname() != null ? user.getFullname() : "") +
                    ", Telephone: " + (user.getTelephoneNo() != null ? user.getTelephoneNo() : ""));
            writer.newLine();
        }
    }

    // Authenticates a user by checking username and password against the file
    public User authenticate(String username, String password) throws IOException {
        List<User> users = readUsers();
        System.out.println("Authenticating user: " + username); // Debug
        for (User user : users) {
            // Return the user if credentials match
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        // Return null if no match is found (wrong password or username)
        return null;
    }

    // Retrieves a user by their username from the file
    public User findByUsername(String username) throws IOException {
        List<User> users = readUsers();
        for (User user : users) {
            // Return the user if username matches
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        // Return null if user is not found
        return null;
    }

    // Reads all users from the file into a list
    private List<User> readUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        // Create the file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Parse the line using regex to extract values
                Pattern pattern = Pattern.compile("Username: (.*?)(?:, Email: (.*?))?(?:, Password: (.*?))?(?:, Full Name: (.*?))?(?:, Telephone: (.*?))?");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String username = matcher.group(1) != null ? matcher.group(1).trim() : "";
                    String email = matcher.group(2) != null ? matcher.group(2).trim() : "";
                    String password = matcher.group(3) != null ? matcher.group(3).trim() : "";
                    String fullname = matcher.group(4) != null ? matcher.group(4).trim() : "";
                    String telephoneNo = matcher.group(5) != null ? matcher.group(5).trim() : "";
                    // Create a new User object with the parsed values
                    User user = new User(username, email, password, fullname, telephoneNo);
                    users.add(user);
                }
            }
        }
        return users;
    }

    // Deletes a user from the file
    public void deleteUser(String username) throws IOException {
        List<User> users = readUsers();
        users.removeIf(user -> user.getUsername().equals(username));
        // Rewrite the file with the updated list
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write("Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Password: " + user.getPassword() +
                        ", Full Name: " + (user.getFullname() != null ? user.getFullname() : "") +
                        ", Telephone: " + (user.getTelephoneNo() != null ? user.getTelephoneNo() : ""));
                writer.newLine();
            }
        }
    }
}