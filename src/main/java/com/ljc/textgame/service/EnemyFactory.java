package com.ljc.textgame.service;

import com.ljc.textgame.domain.Enemy;

import java.util.Random;

public class EnemyFactory {
    private final Random random = new Random();

    public Enemy createRandomEnemy() {
        int type = random.nextInt(3);

        switch (type) {
            case 0:
                return new Enemy("史莱姆", 60, 12, 2, 20, 10);
            case 1:
                return new Enemy("野狼", 80, 16, 4, 30, 15);
            default:
                return new Enemy("哥布林", 100, 20, 5, 40, 20);
        }
    }
}