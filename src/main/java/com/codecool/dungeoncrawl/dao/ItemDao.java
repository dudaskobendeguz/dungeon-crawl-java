package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import java.util.List;

public interface ItemDao {
    void add(ItemModel itemModel, int saveSlotId);
    void update(ItemModel itemModel);
    List<ItemModel> getAllBySaveSlotId(int saveSlotId);
    List<ItemModel> getAll();
}
