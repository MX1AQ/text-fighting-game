package com.ljc.textgame.domain;

public class Player {
    private final String username;
    private String name;
    private int level;
    private int exp;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int gold;

    public Player(String username) {
        this.username = username;
        this.name = username;
        this.level = 1;
        this.exp = 0;
        this.maxHp = 100;
        this.hp = maxHp;
        this.attack = 18;
        this.defense = 5;
        this.gold = 0;
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

    public void healFull() {
        hp = maxHp;
    }

    public void addGold(int value) {
        gold += value;
    }

    public void addExp(int value) {
        exp += value;
        while (exp >= level * 50) {
            exp -= level * 50;
            level++;
            maxHp += 20;
            attack += 5;
            defense += 2;
            hp = maxHp;
            System.out.println("恭喜升级！当前等级：" + level);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
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

    public int getGold() {
        return gold;
    }
}