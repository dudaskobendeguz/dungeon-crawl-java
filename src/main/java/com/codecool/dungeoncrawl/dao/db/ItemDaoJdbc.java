package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoJdbc implements ItemDao {

    private final DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel itemModel, int saveSlotId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (save_slot_id, type_id, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, saveSlotId);
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
    public List<ItemModel> getAllBySaveSlotId(int saveSlotId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM item WHERE save_slot_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, saveSlotId);

            ResultSet rs = st.executeQuery();
            List<ItemModel> itemModels = new ArrayList<>();
            while (rs.next()) {
                ItemModel itemModel = new ItemModel(
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5)
                );
                itemModel.setId(rs.getInt(1));
                itemModels.add(itemModel);
            }
            return itemModels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemModel> getAll() {
        return null;
    }
}
