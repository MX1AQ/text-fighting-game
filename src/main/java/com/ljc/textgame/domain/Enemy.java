package com.ljc.textgame.domain;

public class Enemy {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attack;
    private final int defense;
    private final int expReward;
    private final int goldReward;

    public Enemy(String name, int maxHp, int attack, int defense, int expReward, int goldReward) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getExpReward() {
        return expReward;
    }

    public int getGoldReward() {
        return goldReward;
    }
}