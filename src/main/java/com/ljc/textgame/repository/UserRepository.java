package com.ljc.textgame.repository;

import com.ljc.textgame.domain.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String DATA_DIR = "data";
    private static final String USER_FILE = DATA_DIR + File.separator + "users.txt";

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        loadUsers();
    }

    public void add(User user) {
        users.add(user);
        saveUsers();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }

    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    private void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("读取用户文件失败：" + e.getMessage());
        }
    }

    private void saveUsers() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(formatUser(user));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存用户文件失败：" + e.getMessage());
        }
    }

    private String formatUser(User user) {
        return user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.isActive();
    }

    private User parseUser(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 4) {
            return null;
        }

        User user = new User();
        user.setId(parts[0]);
        user.setUsername(parts[1]);
        user.setPassword(parts[2]);
        user.setActive(Boolean.parseBoolean(parts[3]));
        return user;
    }
}