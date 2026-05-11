package com.ljc.textgame;

import com.ljc.textgame.repository.UserRepository;
import com.ljc.textgame.service.UserService;
import com.ljc.textgame.ui.LoginUI;

public class App {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        LoginUI loginUI = new LoginUI(userService);
        loginUI.start();
    }
}
