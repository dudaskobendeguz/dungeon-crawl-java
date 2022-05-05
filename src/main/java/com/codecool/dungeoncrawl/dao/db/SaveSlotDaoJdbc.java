package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveSlotDaoJdbc implements SaveSlotDao {
    private final DataSource dataSource;


    public SaveSlotDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int add(SaveSlotModel state) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO save_slot (level_id, name) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, state.getLevelId());
            preparedStatement.setString(2, state.getName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throw new RuntimeException("Error under adding save slot to database: " + throwables, throwables);
        }
    }

    @Override
    public void update(SaveSlotModel state) {

    }

    @Override
    public SaveSlotModel get(int saveSlotId) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT level_id, name FROM save_slot WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, saveSlotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            return new SaveSlotModel(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException throwables) {
            throw new RuntimeException("Error under get save slot from database", throwables);
        }
    }

    @Override
    public List<SaveSlotModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, level_id, saved_at FROM save_slot";
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            List<SaveSlotModel> saveSlotModels = new ArrayList<>();
            while (rs.next()) {
                SaveSlotModel saveSlotModel = new SaveSlotModel(
                        rs.getInt(3),
                        rs.getString(2)
                );
                saveSlotModel.setId(rs.getInt(1));
                saveSlotModel.setSavedAt(rs.getDate(4));
                saveSlotModels.add(saveSlotModel);
            }
            return  saveSlotModels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
