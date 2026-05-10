package ui;

import domain.User;
import service.LoginResult;
import service.UserService;
import service.ValidationResult;

import java.util.Scanner;

public class LoginUI {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public LoginUI(UserService userService) {
        this.userService = userService;
    }

    public void start() {
        System.out.println("欢迎来到文字格斗游戏。");

        while (true) {
            showMenu();
            int choice = readInt();
            scanner.nextLine();

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
        System.out.print("请选择操作：");
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
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        String verificationCode = userService.generateVerificationCode();
        System.out.println("请输入验证码：" + verificationCode);
        String inputCode = scanner.nextLine();

        if (!verificationCode.equalsIgnoreCase(inputCode)) {
            System.out.println("验证码错误。");
            return;
        }

        LoginResult result = userService.login(username, password);
        System.out.println(result.getMessage());
        if (result.isSuccess()) {
            User user = result.getUser();
            System.out.println("欢迎回来，" + user.getUsername() + "。");
        }
    }

    private String promptValidUsername() {
        while (true) {
            System.out.print("请输入用户名：");
            String username = scanner.nextLine();
            ValidationResult result = userService.validateUsername(username);
            if (result.isValid()) {
                return username;
            }
            System.out.println(result.getMessage());
        }
    }

    private String promptValidPassword() {
        while (true) {
            System.out.print("请输入密码：");
            String password = scanner.nextLine();
            System.out.print("请再次输入密码：");
            String confirmPassword = scanner.nextLine();

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

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("请输入数字选项。");
            scanner.nextLine();
            System.out.print("请选择操作：");
        }
        return scanner.nextInt();
    }
}
