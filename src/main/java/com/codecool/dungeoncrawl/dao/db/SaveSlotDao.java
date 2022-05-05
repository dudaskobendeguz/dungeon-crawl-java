package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.*;

import java.util.List;

public interface SaveSlotDao {
    int add(SaveSlotModel SaveSlotModel);
    void update(SaveSlotModel state);
    SaveSlotModel get(int saveSlotId);
    SaveSlotModel getByName(String name);
    List<SaveSlotModel> getAll();
    void delete(int saveSlotId);
}
