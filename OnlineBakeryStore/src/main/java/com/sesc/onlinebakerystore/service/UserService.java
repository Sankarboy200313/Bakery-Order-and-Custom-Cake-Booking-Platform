package com.sesc.onlinebakerystore.service;

import com.sesc.onlinebakerystore.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private static final String DATA_FILE_NAME = "Profile_Details.txt";
    private static final User DEFAULT_ADMIN = new User(
            "admin",
            "admin@bakery.local",
            "admin123",
            "Bakery Admin",
            "0000000000",
            true
    );

    @Value("${bakery.store.data-dir:${user.home}/.online-bakery-store}")
    private String dataDirectory;

    @PostConstruct
    public void initializeStorage() throws IOException {
        Path dataFile = getDataFile();
        Files.createDirectories(dataFile.getParent());
        if (Files.notExists(dataFile)) {
            seedFromClasspath(dataFile);
        }

        List<User> users = loadUsers();
        if (users.stream().noneMatch(user -> "admin".equalsIgnoreCase(user.getUsername()))) {
            users.add(DEFAULT_ADMIN);
            writeUsers(users);
        }
    }

    public synchronized void saveUser(User user) throws IOException {
        List<User> users = loadUsers();
        boolean exists = users.stream().anyMatch(existing -> existing.getUsername().equalsIgnoreCase(user.getUsername()));
        if (exists) {
            throw new IOException("Username already exists");
        }
        users.add(user);
        writeUsers(users);
    }

    public synchronized User authenticate(String username, String password) throws IOException {
        return loadUsers().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public synchronized User findByUsername(String username) throws IOException {
        return loadUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public synchronized List<User> readUsers() throws IOException {
        return new ArrayList<>(loadUsers());
    }

    public synchronized void deleteUser(String username) throws IOException {
        List<User> users = loadUsers();
        users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        writeUsers(users);
    }

    public synchronized void updateUser(String oldUsername, User updatedUser) throws IOException {
        List<User> users = loadUsers();
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(oldUsername)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IOException("User not found");
        }

        boolean duplicateUsername = users.stream()
                .anyMatch(existing -> !existing.getUsername().equalsIgnoreCase(oldUsername)
                        && existing.getUsername().equalsIgnoreCase(updatedUser.getUsername()));
        if (duplicateUsername) {
            throw new IOException("Username already exists");
        }

        users.set(index, updatedUser);
        writeUsers(users);
    }

    private Path getDataFile() {
        return Paths.get(dataDirectory).resolve(DATA_FILE_NAME);
    }

    private List<User> loadUsers() throws IOException {
        Path dataFile = getDataFile();
        if (Files.notExists(dataFile)) {
            Files.createDirectories(dataFile.getParent());
            Files.createFile(dataFile);
        }

        List<User> users = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(dataFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseLine(line.trim());
                if (user != null) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    private void writeUsers(List<User> users) throws IOException {
        Path dataFile = getDataFile();
        Files.createDirectories(dataFile.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(
                dataFile,
                StandardCharsets.UTF_8
        )) {
            for (User user : users) {
                writer.write(serialize(user));
                writer.newLine();
            }
        }
    }

    private void seedFromClasspath(Path dataFile) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/Profile_Details.txt")) {
            if (inputStream != null) {
                Files.copy(inputStream, dataFile, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.createFile(dataFile);
            }
        }
    }

    private String serialize(User user) {
        return String.join("|",
                escape(user.getUsername()),
                escape(user.getEmail()),
                escape(user.getPassword()),
                escape(user.getFullname()),
                escape(user.getTelephoneNo()),
                Boolean.toString(user.isAdmin()));
    }

    private User parseLine(String line) {
        if (line.isEmpty()) {
            return null;
        }

        if (line.contains("|")) {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 6) {
                return null;
            }
            return new User(
                    unescape(parts[0]),
                    unescape(parts[1]),
                    unescape(parts[2]),
                    unescape(parts[3]),
                    unescape(parts[4]),
                    Boolean.parseBoolean(parts[5])
            );
        }

        Pattern pattern = Pattern.compile(
                "Username: (.*?)(?:, Email: (.*?))?(?:, Password: (.*?))?(?:, Full Name: (.*?))?(?:, Telephone: (.*?))?$"
        );
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            String username = valueOrEmpty(matcher.group(1));
            String email = valueOrEmpty(matcher.group(2));
            String password = valueOrEmpty(matcher.group(3));
            String fullname = valueOrEmpty(matcher.group(4));
            String telephoneNo = valueOrEmpty(matcher.group(5));
            return new User(username, email, password, fullname, telephoneNo, false);
        }
        return null;
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    private String unescape(String value) {
        return Objects.requireNonNullElse(value, "").trim();
    }
}
