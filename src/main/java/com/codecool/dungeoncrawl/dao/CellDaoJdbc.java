package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.CellModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CellDaoJdbc implements CellDao {
    private final DataSource dataSource;

    public CellDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(CellModel cellModel) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO cell (type_id, x, y) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cellModel.getTypeId());
            preparedStatement.setInt(2, cellModel.getX());
            preparedStatement.setInt(3, cellModel.getY());
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
    public CellModel get(int id) {
        return null;
    }

    @Override
    public List<CellModel> getAll() {
        return null;
    }
}
