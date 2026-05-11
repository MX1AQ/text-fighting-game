package com.ljc.textgame.domain;

import java.util.Random;

public class User {
    private static final String ID_PREFIX = "LJC";
    private static final int ID_RANDOM_LENGTH = 5;

    private String id;
    private String username;
    private String password;
    private boolean active;

    public User() {
        this.id = generateId();
        this.active = true;
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    private String generateId() {
        StringBuilder builder = new StringBuilder(ID_PREFIX);
        Random random = new Random();
        for (int i = 0; i < ID_RANDOM_LENGTH; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
