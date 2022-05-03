package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.TileModel;

import java.util.List;

public interface TileDao {
    void add(TileModel tileModel);
    void update(TileModel tileModel);
    TileModel get(int id);
    List<TileModel> getAll();
}
