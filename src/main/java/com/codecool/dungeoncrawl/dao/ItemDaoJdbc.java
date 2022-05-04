package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ItemDaoJdbc implements ItemDao {

    private final DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel itemModel, int gameStateId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (game_state_id, type_id, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, gameStateId);
            st.setInt(2, itemModel.getTileId());
            st.setInt(3, itemModel.getX());
            st.setInt(4, itemModel.getY());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            itemModel.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemModel itemModel) {

    }

    @Override
    public List<ItemModel> getAllByGameStateId(int gameStateId) {
        return null;
    }

    @Override
    public List<ItemModel> getAll() {
        return null;
    }
}
