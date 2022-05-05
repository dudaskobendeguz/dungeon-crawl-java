package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.MonsterModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterDaoJdbc implements MonsterDao {

    private final DataSource dataSource;

    public MonsterDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MonsterModel monsterModel, int saveSlotId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO monster" +
                    "(save_slot_id, type_id, x, y, hp, timer, direction) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, saveSlotId);
            st.setInt(2, monsterModel.getTypeId());
            st.setInt(3, monsterModel.getX());
            st.setInt(4, monsterModel.getY());
            st.setInt(5, monsterModel.getHp());
            st.setInt(6, monsterModel.getMoveTimer());
            st.setInt(7, monsterModel.getDirectionId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            monsterModel.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MonsterModel monsterModel) {

    }

    @Override
    public List<MonsterModel> getAllBySaveSlotId(int saveSlotId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM monster WHERE save_slot_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, saveSlotId);

            ResultSet rs = st.executeQuery();
            List<MonsterModel> monsterModels = new ArrayList<>();
            while (rs.next()) {
                MonsterModel monsterModel = new MonsterModel
                        (
                                rs.getInt(3), // type_id
                                rs.getInt(4), // x
                                rs.getInt(5), // y
                                rs.getInt(6) // hp
                        );
                monsterModel.setId(rs.getInt(1));
                monsterModel.setMoveTimer(rs.getInt(7));
                monsterModel.setDirectionId(rs.getInt(8));
                monsterModels.add(monsterModel);
            }
            return monsterModels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MonsterModel> getAll() {
        return null;
    }
}
