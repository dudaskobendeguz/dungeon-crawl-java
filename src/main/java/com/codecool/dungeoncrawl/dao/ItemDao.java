package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.CellModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import java.util.List;

public interface ItemDao {
    void add(ItemModel itemModel, int gameStateId);
    void update(ItemModel itemModel);
    List<ItemModel> getAllByGameStateId(int gameStateId);
    List<ItemModel> getAll();
}
