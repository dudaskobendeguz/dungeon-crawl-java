package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private final DataSource dataSource;


    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int add(GameState state) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO game_state (level_id) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, state.getLevelId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throw new RuntimeException("Error under adding game state to database: " + throwables, throwables);
        }
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
