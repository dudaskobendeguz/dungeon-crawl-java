package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.Level;

import java.util.Arrays;
import java.util.List;

public class GameStateModel {
    private final SaveSlotModel saveSlotModel;
    private final PlayerModel playerModel;
    private final List<CellModel> cellModels;
    private final List<MonsterModel> monsterModels;
    private final List<ItemModel> itemModels;

    public GameStateModel(
            SaveSlotModel saveSlotModel,
            PlayerModel playerModel,
            List<CellModel> cellModels,
            List<MonsterModel> monsterModels,
            List<ItemModel> itemModels
    )
    {
        this.saveSlotModel = saveSlotModel;
        this.playerModel = playerModel;
        this.cellModels = cellModels;
        this.monsterModels = monsterModels;
        this.itemModels = itemModels;
    }

    public SaveSlotModel getSaveSlotModel() {
        return saveSlotModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public List<CellModel> getCellModels() {
        return cellModels;
    }

    public List<MonsterModel> getMonsterModels() {
        return monsterModels;
    }

    public List<ItemModel> getItemModels() {
        return itemModels;
    }

    public int getSaveSlotId() {
        return saveSlotModel.getId();
    }

    public Level getLevel() {
        return Arrays.stream(Level.values())
                .filter(level -> level.getID() == saveSlotModel.getLevelId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
