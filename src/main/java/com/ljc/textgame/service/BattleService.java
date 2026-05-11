package com.ljc.textgame.service;

import com.ljc.textgame.domain.Enemy;
import com.ljc.textgame.domain.Player;

public class BattleService {

    public int playerAttack(Player player, Enemy enemy) {
        int damage = calculateDamage(player.getAttack(), enemy.getDefense());
        enemy.takeDamage(damage);
        return damage;
    }

    public int enemyAttack(Enemy enemy, Player player, boolean defending) {
        int defense = player.getDefense();

        if (defending) {
            defense += 8;
        }

        int damage = calculateDamage(enemy.getAttack(), defense);
        player.takeDamage(damage);
        return damage;
    }

    public void settleVictory(Player player, Enemy enemy) {
        player.addExp(enemy.getExpReward());
        player.addGold(enemy.getGoldReward());
        player.healFull();
    }

    private int calculateDamage(int attack, int defense) {
        int damage = attack - defense;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }
}