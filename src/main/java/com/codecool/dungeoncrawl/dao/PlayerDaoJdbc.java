package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private final DataSource dataSource;

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
            statement.setInt(6, player.getDirectionTypeId());
            statement.setInt(7, player.getCellTypeId());
            statement.setInt(8, player.getWeaponTypeId());
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
        try (Connection connection = dataSource.getConnection()) {
            //TODO this query is a dummy query(it gives back the last added player)
            String sqlQuery = "SELECT * FROM player ORDER BY id DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Array itemsPgArray = resultSet.getArray(10);
            List<Integer> itemsIds = new ArrayList<>(List.of((Integer[])itemsPgArray.getArray()));
            return new PlayerModel(
                    resultSet.getString(2), // playerName
                    resultSet.getInt(3), // hp
                    resultSet.getInt(4), // maxHp
                    resultSet.getInt(5), // fireballTimer
                    resultSet.getInt(6), // damage
                    resultSet.getInt(7), // directionType
                    resultSet.getInt(8), // cellType
                    resultSet.getInt(9), // weaponType
                    itemsIds, // items
                    resultSet.getInt(11), // x
                    resultSet.getInt(12) // y
            );
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
