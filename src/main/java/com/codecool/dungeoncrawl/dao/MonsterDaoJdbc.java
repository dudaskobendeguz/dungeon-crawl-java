package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MonsterModel;

import javax.sql.DataSource;
import java.util.List;

public class MonsterDaoJdbc implements MonsterDao {

    private final DataSource dataSource;

    public MonsterDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MonsterModel monsterModel, int gameState) {

    }

    @Override
    public void update(MonsterModel monsterModel) {

    }

    @Override
    public MonsterModel get(int id) {
        return null;
    }

    @Override
    public List<MonsterModel> getAll() {
        return null;
    }
}
