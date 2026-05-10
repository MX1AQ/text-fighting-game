import repository.UserRepository;
import service.UserService;
import ui.LoginUI;

public class App {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        LoginUI loginUI = new LoginUI(userService);
        loginUI.start();
    }
}
