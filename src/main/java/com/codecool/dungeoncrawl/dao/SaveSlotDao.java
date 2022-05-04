package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.saveSlotModel;

import java.util.List;

public interface SaveSlotDao {
    int add(saveSlotModel state);
    void update(saveSlotModel state);
    saveSlotModel get(int saveSlotId);
    List<saveSlotModel> getAll();
}
