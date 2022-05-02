package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO " +
                    "player (" +
                    "player_name," +
                    "hp, " +
                    "max_hp, " +
                    "fireball_timer, " +
                    "damage, " +
                    "direction_type, " +
                    "cell_type, " +
                    "weapon_type, " +
                    "items, " +
                    "x, " +
                    "y ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getMaxHp());
            statement.setInt(4, player.getFireballTimer());
            statement.setInt(5, player.getDamage());
            statement.setString(6, player.getDirectionType());
            statement.setInt(7, player.getCellType());
            statement.setInt(8, player.getWeaponType());
            Array items = conn.createArrayOf("INTEGER", player.getItems().toArray(new Integer[0]));
            statement.setArray(9, items);
            statement.setInt(10, player.getX());
            statement.setInt(11, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
