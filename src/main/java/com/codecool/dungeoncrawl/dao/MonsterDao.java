package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MonsterModel;

import java.util.List;

public interface MonsterDao {
    void add(MonsterModel monsterModel);
    void update(MonsterModel monsterModel);
    MonsterModel get(int id);
    List<MonsterModel> getAll();
}
