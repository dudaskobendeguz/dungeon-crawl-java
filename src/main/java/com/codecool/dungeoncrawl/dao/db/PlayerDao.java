package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player, int saveSlotId);
    void update(PlayerModel player);
    PlayerModel get(int saveSlotId);
    List<PlayerModel> getAll();
}
