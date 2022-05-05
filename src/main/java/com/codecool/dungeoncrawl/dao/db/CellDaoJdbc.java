package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.CellModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CellDaoJdbc implements CellDao {
    private final DataSource dataSource;

    public CellDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(CellModel cellModel, int saveSlotId) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO cell (save_slot_id, tile_id, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, saveSlotId);
            preparedStatement.setInt(2, cellModel.getTypeId());
            preparedStatement.setInt(3, cellModel.getX());
            preparedStatement.setInt(4, cellModel.getY());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            cellModel.setId(resultSet.getInt(1));
        } catch (SQLException throwables) {
            throw new RuntimeException("Error under add cell to database:  " + throwables, throwables);
        }
    }

    @Override
    public void update(CellModel cellModel) {

    }

    @Override
    public List<CellModel> getAllBySaveSlotId(int saveSlotId) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM cell WHERE save_slot_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, saveSlotId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CellModel> cellModels = new ArrayList<>();
            while (resultSet.next()) {
                cellModels.add(new CellModel(
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5)
                ));
            }
            return cellModels;
            
        } catch (SQLException throwables) {
            throw new RuntimeException("Error under get all cell by game state id database -" + throwables, throwables);
        }
    }

    @Override
    public List<CellModel> getAll() {
        return null;
    }
}
