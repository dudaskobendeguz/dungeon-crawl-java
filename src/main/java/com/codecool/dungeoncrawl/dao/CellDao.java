package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.CellModel;

import java.util.List;

public interface CellDao {
    void add(CellModel cellModel, int gameStateId);
    void update(CellModel cellModel);
    List<CellModel> getAllByGameStateId(int gameStateId);
    List<CellModel> getAll();
}
