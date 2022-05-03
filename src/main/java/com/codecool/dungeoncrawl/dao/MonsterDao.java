package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MonsterModel;

import java.util.List;

public interface MonsterDao {
    void add(MonsterModel monsterModel, int gameStateId);
    void update(MonsterModel monsterModel);
    List<MonsterModel> getAllByGameStateId(int gameStateId);
    List<MonsterModel> getAll();
}
