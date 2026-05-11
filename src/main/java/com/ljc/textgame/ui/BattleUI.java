package com.ljc.textgame.ui;

import com.ljc.textgame.domain.Enemy;
import com.ljc.textgame.domain.Player;
import com.ljc.textgame.service.BattleService;
import com.ljc.textgame.service.EnemyFactory;

public class BattleUI {
    private final InputHelper inputHelper;
    private final BattleService battleService;
    private final EnemyFactory enemyFactory;

    public BattleUI(InputHelper inputHelper, BattleService battleService, EnemyFactory enemyFactory) {
        this.inputHelper = inputHelper;
        this.battleService = battleService;
        this.enemyFactory = enemyFactory;
    }

    public void startBattle(Player player) {
        Enemy enemy = enemyFactory.createRandomEnemy();
        System.out.println("遭遇敌人：" + enemy.getName());

        while (!player.isDead() && !enemy.isDead()) {
            showBattleStatus(player, enemy);
            showBattleMenu();

            int choice = inputHelper.readIntInRange("请选择战斗操作：", 1, 3);
            boolean defending = false;

            switch (choice) {
                case 1:
                    int playerDamage = battleService.playerAttack(player, enemy);
                    System.out.println(player.getName() + " 对 " + enemy.getName() + " 造成 " + playerDamage + " 点伤害。");
                    break;
                case 2:
                    defending = true;
                    System.out.println(player.getName() + " 进入防御状态，本回合受到的伤害降低。");
                    break;
                case 3:
                    System.out.println("你逃离了战斗。");
                    return;
                default:
                    System.out.println("无效操作。");
            }

            if (enemy.isDead()) {
                battleService.settleVictory(player, enemy);
                System.out.println("战斗胜利！获得经验 " + enemy.getExpReward() + "，金币 " + enemy.getGoldReward() + "。");
                return;
            }

            int enemyDamage = battleService.enemyAttack(enemy, player, defending);
            System.out.println(enemy.getName() + " 对 " + player.getName() + " 造成 " + enemyDamage + " 点伤害。");
        }

        if (player.isDead()) {
            System.out.println("你战败了，角色生命值已恢复，重新整备后再战。");
            player.healFull();
        }
    }

    private void showBattleStatus(Player player, Enemy enemy) {
        System.out.println("------------------------");
        System.out.println(player.getName() + " HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println(enemy.getName() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
        System.out.println("------------------------");
    }

    private void showBattleMenu() {
        System.out.println("1. 普通攻击");
        System.out.println("2. 防御");
        System.out.println("3. 逃跑");
    }
}