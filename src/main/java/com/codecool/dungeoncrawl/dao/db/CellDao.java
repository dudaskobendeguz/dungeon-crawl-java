package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.CellModel;

import java.util.List;

public interface CellDao {
    void add(CellModel cellModel, int saveSlotId);
    void update(CellModel cellModel);
    List<CellModel> getAllBySaveSlotId(int saveSlotId);
    List<CellModel> getAll();
}
