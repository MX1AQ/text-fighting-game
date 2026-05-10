package service;

import domain.User;
import repository.UserRepository;

import java.util.Random;

public class UserService {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 8;
    private static final int VERIFICATION_CODE_LENGTH = 5;
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ValidationResult validateUsername(String username) {
        if (!isLengthValid(username)) {
            return ValidationResult.invalid("用户名长度必须在 3 到 8 位之间。");
        }

        if (!isAlphaNumeric(username)) {
            return ValidationResult.invalid("用户名只能由字母和数字组成。");
        }

        if (isPureDigits(username)) {
            return ValidationResult.invalid("用户名不能是纯数字。");
        }

        if (userRepository.existsByUsername(username)) {
            return ValidationResult.invalid("用户名已存在，请重新输入。");
        }

        return ValidationResult.valid();
    }

    public ValidationResult validatePassword(String password) {
        if (!isLengthValid(password)) {
            return ValidationResult.invalid("密码长度必须在 3 到 8 位之间。");
        }

        if (!isAlphaNumeric(password)) {
            return ValidationResult.invalid("密码只能由字母和数字组成。");
        }

        if (isPureDigits(password)) {
            return ValidationResult.invalid("密码不能是纯数字。");
        }

        return ValidationResult.valid();
    }

    public User register(String username, String password) {
        User user = new User(username, password);
        userRepository.add(user);
        return user;
    }

    public LoginResult login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return LoginResult.failed("用户名不存在。");
        }

        if (!user.isActive()) {
            return LoginResult.failed("当前用户已被禁用。");
        }

        if (!user.getPassword().equals(password)) {
            return LoginResult.failed("密码错误。");
        }

        return LoginResult.success(user);
    }

    public String generateVerificationCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        int digitPosition = random.nextInt(VERIFICATION_CODE_LENGTH);

        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            if (i == digitPosition) {
                code.append(random.nextInt(10));
            } else {
                code.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
            }
        }

        return code.toString();
    }

    public int getUserCount() {
        return userRepository.getAll().size();
    }

    private boolean isLengthValid(String value) {
        return value != null && value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
    }

    private boolean isAlphaNumeric(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isPureDigits(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
