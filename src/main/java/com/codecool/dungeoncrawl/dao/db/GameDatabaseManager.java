package com.codecool.dungeoncrawl.dao.db;

import com.codecool.dungeoncrawl.Level;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.monsters.MoveDirection;
import com.codecool.dungeoncrawl.logic.actor.monsters.MoveTimer;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.*;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {
    private SaveSlotDao saveSlotDao;
    private PlayerDao playerDao;
    private CellDao cellDao;
    private MonsterDao monsterDao;
    private ItemDao itemDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        saveSlotDao = new SaveSlotDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource);
        cellDao = new CellDaoJdbc(dataSource);
        monsterDao = new MonsterDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);
    }

    public void saveGame(GameMap map, Level currentLevel, String name) {
        int saveSlotId = saveGameState(currentLevel, name);
        savePlayer(map.getPlayer(), saveSlotId);
        saveCells(map, saveSlotId);
        saveMonsters(map, saveSlotId);
        saveItems(map, saveSlotId);
    }

    private int saveGameState(Level currentLevel, String name) {
        SaveSlotModel saveSlotModel = new SaveSlotModel(currentLevel.getID(), name);
        return saveSlotDao.add(saveSlotModel);

    }

    public void savePlayer(Player player, int saveSlotId) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model, saveSlotId);
    }

    private void saveCells(GameMap map, int saveSlotId) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.getCell(x, y).getType().isChanged()) {
                    CellModel cellModel = new CellModel(map.getCell(x, y).getTileId(), x, y);
                    cellDao.add(cellModel, saveSlotId);
                }
            }
        }
    }

    private void saveMonsters(GameMap map, int saveSlotId) {
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
            monsterDao.add(monsterModel, saveSlotId);
        }
    }

    private void saveItems(GameMap map, int saveSlotId) {
        for (Item item : map.getItems()) {
            ItemModel itemModel = new ItemModel(
                    item.getTileId(),
                    item.getX(),
                    item.getY()
            );
            itemDao.add(itemModel, saveSlotId);
        }
    }


    public GameMap loadGame(int saveSlotId) {
        SaveSlotModel saveSlotModel = loadSaveSlot(saveSlotId);
        PlayerModel playerModel = loadPlayer(saveSlotId);
        List<CellModel> cellModels = loadCells(saveSlotId);
        List<MonsterModel> monsterModels = loadMonsters(saveSlotId);
        List<ItemModel> itemModels = loadItems(saveSlotId);
        GameStateModel gameStateModel = new GameStateModel(saveSlotModel, playerModel, cellModels, monsterModels, itemModels);
        return MapLoader.getGameMap(gameStateModel);
    }

    private List<ItemModel> loadItems(int saveSlotId) {
        return itemDao.getAllBySaveSlotId(saveSlotId);
    }

    private List<MonsterModel> loadMonsters(int saveSlotId) {
        return monsterDao.getAllBySaveSlotId(saveSlotId);
    }


    private List<CellModel> loadCells(int saveSlotId) {
        return cellDao.getAllBySaveSlotId(saveSlotId);
    }

    private SaveSlotModel loadSaveSlot(int saveSlotId) {
        return saveSlotDao.get(saveSlotId);
    }

    public PlayerModel loadPlayer(int saveSlotId) {
        return playerDao.get(saveSlotId);
    }

    /**
     * Trying to make connection with the database.
     * The connection needs environment variables:
     * <p>
     * DB_NAME: the name of the current database
     * <p>
     * DB_USERNAME: name of the database's user
     * <p>
     * DB_PASSWORD: the password of the database's user.
     * <p>
     * Create these environment variables before running the program!!!
     *
     * @return connected DataSource object
     * @throws SQLException if the connection failed
     */
    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("DB_NAME");
        String user = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
