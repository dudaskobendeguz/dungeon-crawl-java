package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.*;

import java.util.List;

public interface SaveSlotDao {
    int add(SaveSlotModel SaveSlotModel);
    void update(SaveSlotModel state);
    SaveSlotModel get(int saveSlotId);
    List<SaveSlotModel> getAll();
}
