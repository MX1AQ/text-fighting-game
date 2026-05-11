package com.ljc.textgame.ui;

import com.ljc.textgame.domain.Player;
import com.ljc.textgame.domain.User;
import com.ljc.textgame.service.GameService;

public class GameUI {
    private final InputHelper inputHelper;
    private final GameService gameService;
    private final BattleUI battleUI;

    public GameUI(InputHelper inputHelper, GameService gameService, BattleUI battleUI) {
        this.inputHelper = inputHelper;
        this.gameService = gameService;
        this.battleUI = battleUI;
    }

    public void start(User user) {
        Player player = gameService.getOrCreatePlayer(user);

        while (true) {
            showGameMenu(player);
            int choice = inputHelper.readIntInRange("请选择操作：", 1, 3);

            switch (choice) {
                case 1:
                    showPlayerInfo(player);
                    break;
                case 2:
                    battleUI.startBattle(player);
                    break;
                case 3:
                    System.out.println("已退出当前账号。");
                    return;
                default:
                    System.out.println("无效选择。");
            }
        }
    }

    private void showGameMenu(Player player) {
        System.out.println("========================");
        System.out.println("当前角色：" + player.getName());
        System.out.println("1. 查看角色信息");
        System.out.println("2. 开始战斗");
        System.out.println("3. 退出登录");
        System.out.println("========================");
    }

    private void showPlayerInfo(Player player) {
        System.out.println("角色名：" + player.getName());
        System.out.println("等级：" + player.getLevel());
        System.out.println("经验：" + player.getExp());
        System.out.println("生命值：" + player.getHp() + "/" + player.getMaxHp());
        System.out.println("攻击力：" + player.getAttack());
        System.out.println("防御力：" + player.getDefense());
        System.out.println("金币：" + player.getGold());
    }
}