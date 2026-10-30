package com.ljc.textgame.repository;

import com.ljc.textgame.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // 数据库连接信息(把密码改成你自己的)
    private static final String URL = "jdbc:mysql://localhost:3306/textgame?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "517612GYFgyf";

    // 每次操作新建一个连接(学习阶段够用,先不用纠结性能)
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }

    // 新增用户 → INSERT
    public void add(User user) {
        String sql = "INSERT INTO users (id, username, password, active) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("新增用户失败：" + e.getMessage());
        }
    }

    // 判断用户名是否存在
    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }

    // 按用户名查用户 → SELECT
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, active FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toUser(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("查询用户失败：" + e.getMessage());
        }
        return null;
    }

    // 拿所有用户 → SELECT
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        String sql = "SELECT id, username, password, active FROM users";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(toUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("查询所有用户失败：" + e.getMessage());
        }
        return result;
    }

    // 把数据库里的一行转成 User 对象
    private User toUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("active"));
        return user;
    }
}