package com.ljc.textgame.ui;

import com.ljc.textgame.domain.User;
import com.ljc.textgame.service.LoginResult;
import com.ljc.textgame.service.UserService;
import com.ljc.textgame.service.ValidationResult;

public class LoginUI {
    private final UserService userService;
    private final GameUI gameUI;
    private final InputHelper inputHelper;

    public LoginUI(UserService userService, GameUI gameUI, InputHelper inputHelper) {
        this.userService = userService;
        this.gameUI = gameUI;
        this.inputHelper = inputHelper;
    }

    public void start() {
        System.out.println("欢迎来到文字格斗游戏。");

        while (true) {
            showMenu();
            int choice = inputHelper.readIntInRange("请选择操作：", 1, 3);

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegister();
                    break;
                case 3:
                    System.out.println("已退出游戏。");
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private void showMenu() {
        System.out.println("========================");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.println("========================");
    }

    private void handleRegister() {
        System.out.println("进入注册流程。");

        String username = promptValidUsername();
        String password = promptValidPassword();

        User user = userService.register(username, password);
        System.out.println("注册成功，用户编号：" + user.getId());
        System.out.println("当前注册用户数：" + userService.getUserCount());
    }

    private void handleLogin() {
        System.out.println("进入登录流程。");
        String username = inputHelper.readLine("请输入用户名：");
        String password = inputHelper.readLine("请输入密码：");

        String verificationCode = userService.generateVerificationCode();
        System.out.println("请输入验证码：" + verificationCode);
        String inputCode = inputHelper.readLine("验证码：");

        if (!verificationCode.equalsIgnoreCase(inputCode)) {
            System.out.println("验证码错误。");
            return;
        }

        LoginResult result = userService.login(username, password);
        System.out.println(result.getMessage());

        if (result.isSuccess()) {
            User user = result.getUser();
            System.out.println("欢迎回来，" + user.getUsername() + "。");
            gameUI.start(user);
        }
    }

    private String promptValidUsername() {
        while (true) {
            String username = inputHelper.readLine("请输入用户名：");
            ValidationResult result = userService.validateUsername(username);
            if (result.isValid()) {
                return username;
            }
            System.out.println(result.getMessage());
        }
    }

    private String promptValidPassword() {
        while (true) {
            String password = inputHelper.readLine("请输入密码：");
            String confirmPassword = inputHelper.readLine("请再次输入密码：");

            if (!password.equals(confirmPassword)) {
                System.out.println("两次输入的密码不一致。");
                continue;
            }

            ValidationResult result = userService.validatePassword(password);
            if (result.isValid()) {
                return password;
            }
            System.out.println(result.getMessage());
        }
    }
}