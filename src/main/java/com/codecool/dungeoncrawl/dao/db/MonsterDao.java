package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.MonsterModel;

import java.util.List;

public interface MonsterDao {
    void add(MonsterModel monsterModel, int saveSlotId);
    void update(MonsterModel monsterModel);
    List<MonsterModel> getAllBySaveSlotId(int saveSlotId);
    List<MonsterModel> getAll();
}
