package com.ljc.textgame.repository;

import com.ljc.textgame.domain.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/textgame?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "517612GYFgyf";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }

    // 按用户名加载角色(没有则返回 null)
    public Player findByUsername(String username) {
        String sql = "SELECT username, name, level, exp, hp, max_hp, attack, defense, gold FROM players WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toPlayer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("加载角色失败：" + e.getMessage());
        }
        return null;
    }

    // 保存角色(存在则更新,不存在则新增)
    public void save(Player player) {
        // 先看数据库里有没有这个角色
        if (findByUsername(player.getUsername()) == null) {
            insert(player);
        } else {
            update(player);
        }
    }

    private void insert(Player p) {
        String sql = "INSERT INTO players (username, name, level, exp, hp, max_hp, attack, defense, gold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getLevel());
            ps.setInt(4, p.getExp());
            ps.setInt(5, p.getHp());
            ps.setInt(6, p.getMaxHp());
            ps.setInt(7, p.getAttack());
            ps.setInt(8, p.getDefense());
            ps.setInt(9, p.getGold());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("保存角色失败：" + e.getMessage());
        }
    }

    private void update(Player p) {
        String sql = "UPDATE players SET name=?, level=?, exp=?, hp=?, max_hp=?, attack=?, defense=?, gold=? WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getLevel());
            ps.setInt(3, p.getExp());
            ps.setInt(4, p.getHp());
            ps.setInt(5, p.getMaxHp());
            ps.setInt(6, p.getAttack());
            ps.setInt(7, p.getDefense());
            ps.setInt(8, p.getGold());
            ps.setString(9, p.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("更新角色失败：" + e.getMessage());
        }
    }

    // 把数据库一行转成 Player(用到你新加的全字段构造器)
    private Player toPlayer(ResultSet rs) throws SQLException {
        return new Player(
                rs.getString("username"),
                rs.getString("name"),
                rs.getInt("level"),
                rs.getInt("exp"),
                rs.getInt("hp"),
                rs.getInt("max_hp"),
                rs.getInt("attack"),
                rs.getInt("defense"),
                rs.getInt("gold")
        );
    }
}