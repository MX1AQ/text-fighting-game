package com.ljc.textgame;

import com.ljc.textgame.repository.UserRepository;
import com.ljc.textgame.service.BattleService;
import com.ljc.textgame.service.EnemyFactory;
import com.ljc.textgame.service.GameService;
import com.ljc.textgame.service.UserService;
import com.ljc.textgame.ui.BattleUI;
import com.ljc.textgame.ui.GameUI;
import com.ljc.textgame.ui.InputHelper;
import com.ljc.textgame.ui.LoginUI;

public class App {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        InputHelper inputHelper = new InputHelper();

        GameService gameService = new GameService();
        BattleService battleService = new BattleService();
        EnemyFactory enemyFactory = new EnemyFactory();

        BattleUI battleUI = new BattleUI(inputHelper, battleService, enemyFactory);
        GameUI gameUI = new GameUI(inputHelper, gameService, battleUI);
        LoginUI loginUI = new LoginUI(userService, gameUI, inputHelper);

        loginUI.start();
    }
}
