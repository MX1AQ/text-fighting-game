package com.ljc.textgame.service;

import com.ljc.textgame.domain.Enemy;
import com.ljc.textgame.domain.Player;
import com.ljc.textgame.domain.User;
import com.ljc.textgame.repository.PlayerRepository;

public class GameService {
    private final PlayerRepository playerRepository;

    public GameService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getOrCreatePlayer(User user) {
        Player player = playerRepository.findByUsername(user.getUsername());
        if (player == null) {
            player = new Player(user.getUsername());
            playerRepository.save(player);
        }
        return player;
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public Enemy createEnemyFor(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player must not be null");
        }

        int level = Math.max(1, player.getLevel());
        String name = getEnemyName(level);
        int maxHp = 40 + level * 15;
        int attack = 8 + level * 4;
        int defense = 2 + level * 2;
        int expReward = 20 + level * 10;
        int goldReward = 5 + level * 3;

        return new Enemy(name, maxHp, attack, defense, expReward, goldReward);
    }

    private String getEnemyName(int level) {
        if (level <= 2) {
            return "Training Dummy";
        }
        if (level <= 5) {
            return "Forest Bandit";
        }
        return "Arena Champion";
    }
}