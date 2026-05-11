package com.ljc.textgame.service;

import com.ljc.textgame.domain.Player;
import com.ljc.textgame.domain.User;

import java.util.HashMap;
import java.util.Map;

public class GameService {
    private final Map<String, Player> players = new HashMap<>();

    public Player getOrCreatePlayer(User user) {
        Player player = players.get(user.getUsername());

        if (player == null) {
            player = new Player(user.getUsername());
            players.put(user.getUsername(), player);
        }

        return player;
    }
}