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
    public void add(PlayerModel player, int saveSlotId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO " +
                    "player (" +
                    "save_slot_id," +
                    "player_name," +
                    "hp, " +
                    "fireball_timer, " +
                    "direction_type, " +
                    "weapon_type, " +
                    "items, " +
                    "x, " +
                    "y ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, saveSlotId);
            statement.setString(2, player.getPlayerName());
            statement.setInt(3, player.getHp());
            statement.setInt(4, player.getFireballTimer());
            statement.setInt(5, player.getDirectionTypeId());
            statement.setInt(6, player.getWeaponTypeId());
            Array items = conn.createArrayOf("INTEGER", player.getItems().toArray(new Integer[0]));
            statement.setArray(7, items);
            statement.setInt(8, player.getX());
            statement.setInt(9, player.getY());
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
    public PlayerModel get(int saveSlotId) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM player WHERE save_slot_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, saveSlotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Array itemsPgArray = resultSet.getArray(8);
            List<Integer> itemsIds = new ArrayList<>(List.of((Integer[])itemsPgArray.getArray()));
            return new PlayerModel(
                    resultSet.getString(3), // playerName
                    resultSet.getInt(4),    // hp
                    resultSet.getInt(5),    // fireballTimer
                    resultSet.getInt(6),    // directionType
                    resultSet.getInt(7),    // weaponType
                    itemsIds,                          // items
                    resultSet.getInt(9),    // x
                    resultSet.getInt(10)    // y
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
