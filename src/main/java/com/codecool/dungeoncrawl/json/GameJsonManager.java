package com.codecool.dungeoncrawl.json;

import com.codecool.dungeoncrawl.Level;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.monsters.MoveDirection;
import com.codecool.dungeoncrawl.logic.actor.monsters.MoveTimer;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameJsonManager {

    private SaveSlotDaoJson saveSlotDaoJson;

    public void setup() {
        saveSlotDaoJson = new SaveSlotDaoJson();
    }

    public void exportGame(GameMap map, Level currentLevel, String filename) {
        SaveSlotModel saveSlotModel = createSaveSlotModel(currentLevel);
        PlayerModel playerModel = createPlayerModel(map.getPlayer());
        List<CellModel> cellModels = createCellModels(map);
        List<MonsterModel> monsterModels = createMonsterModels(map);
        List<ItemModel> itemModels = createItemModels(map);
        GameStateModel gameStateModel = new GameStateModel(saveSlotModel, playerModel, cellModels, monsterModels, itemModels);
        saveSlotDaoJson.exportToJson(gameStateModel, filename);
    }

    private SaveSlotModel createSaveSlotModel(Level currentLevel) {
        return new SaveSlotModel(currentLevel.getID());
    }

    public PlayerModel createPlayerModel(Player player) {
        return new PlayerModel(player);
    }

    private List<CellModel> createCellModels(GameMap map) {
        List<CellModel> cellModels = new ArrayList<>();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.getCell(x, y).getType().isChanged()) {
                    cellModels.add(new CellModel(map.getCell(x, y).getTileId(), x, y));
                }
            }
        }
        return cellModels;
    }

    private List<MonsterModel> createMonsterModels(GameMap map) {
        List<MonsterModel> monsterModels = new ArrayList<>();
        for (Monster monster : map.getMonsters()) {
            MonsterModel monsterModel = new MonsterModel(
                    monster.getTileId(),
                    monster.getX(),
                    monster.getY(),
                    monster.getHealth()
            );
            if (monster instanceof MoveTimer) {
                int moveTimer = ((MoveTimer) monster).getMoveTimer();
                monsterModel.setMoveTimer(moveTimer);
            }
            if (monster instanceof MoveDirection) {
                int directionId = ((MoveDirection) monster).getMoveDirection().getID();
                monsterModel.setDirectionId(directionId);
            }
            monsterModels.add(monsterModel);
        }
        return monsterModels;
    }

    private List<ItemModel> createItemModels(GameMap map) {
        List<ItemModel> itemModels = new ArrayList<>();
        for (Item item : map.getItems()) {
            ItemModel itemModel = new ItemModel(
                    item.getTileId(),
                    item.getX(),
                    item.getY()
            );
            itemModels.add(itemModel);
        }
        return itemModels;
    }


    public GameMap importGame(String filename) {

        GameStateModel gameStateModel = saveSlotDaoJson.importJson(filename);
        return MapLoader.getGameMap(gameStateModel);
    }

    public List<GameStateModel> getAllExport() {
        return saveSlotDaoJson.getAll();
    }

    public List<String> getAllSaveFileName() {
        return saveSlotDaoJson.getAllFileName();
    }
}
