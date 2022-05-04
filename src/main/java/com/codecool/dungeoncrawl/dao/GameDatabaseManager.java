package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.Level;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.monsters.MoveDirection;
import com.codecool.dungeoncrawl.logic.actor.monsters.MoveTimer;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.items.WeaponType;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.CellModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameDatabaseManager {
    private GameStateDao gameStateDao;
    private PlayerDao playerDao;
    private CellDao cellDao;
    private MonsterDao monsterDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        gameStateDao = new GameStateDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource);
        cellDao = new CellDaoJdbc(dataSource);
        monsterDao = new MonsterDaoJdbc(dataSource);
    }

    public void saveGame(GameMap map, Level currentLevel) {
        int gameStateId = saveGameState(currentLevel);
        savePlayer(map.getPlayer(), gameStateId);
        saveCells(map, gameStateId);
        saveMonsters(map, gameStateId);
    }

    private int saveGameState(Level currentLevel) {
        GameState gameState = new GameState(currentLevel.getID());
        return gameStateDao.add(gameState);

    }

    public void savePlayer(Player player, int gameStateId) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model, gameStateId);
    }

    private void saveCells(GameMap map, int gameStateId) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.getCell(x, y).getType().isChanged()) {
                    CellModel cellModel = new CellModel(map.getCell(x, y).getTileId(), x, y);
                    cellDao.add(cellModel, gameStateId);
                }
            }
        }
    }

    private void saveMonsters(GameMap map, int gameStateId) {
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
            monsterDao.add(monsterModel, gameStateId);
        }
    }

    public GameMap loadGame(int gameStateId) {
        Level loadedLevel = loadGameState(gameStateId);
        PlayerModel playerModel = loadPlayer(gameStateId);
        Player player = createNewPlayer(playerModel);
        List<CellModel> cellModels = loadCells(gameStateId);
//        GameMap map = MapLoader.getGameMap(loadedLevel.getMAP_FILE_PATH(),)
        return null;
    }

    private Player createNewPlayer(PlayerModel playerModel) {
        List<Item> items = createItemsFromData(playerModel.getItems());
        Cell cell = createCellFromData(playerModel.getX(), playerModel.getY(), playerModel.getCellTypeId());
        Weapon weapon = createWeaponFromData(playerModel.getWeaponTypeId());
        Direction direction = createDirectionFromData(playerModel.getDirectionTypeId());
        return new Player(playerModel, cell, direction, weapon, items);
    }

    private Direction createDirectionFromData(int directionTypeId) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.getID() == directionTypeId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Weapon createWeaponFromData(int weaponTypeId) {
        WeaponType weaponType = getWeaponType(weaponTypeId);
        return new Weapon(null, weaponType);
    }

    private WeaponType getWeaponType(int weaponTypeId) {
        return Arrays.stream(WeaponType.values())
                .filter(weaponType -> weaponType.getTileId() == weaponTypeId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Cell createCellFromData(int x, int y, int cellTypeId) {
        CellType cellType = getCellType(cellTypeId);
        return new Cell(x, y, cellType);
    }

    private CellType getCellType(int cellTypeId) {
        return Arrays.stream(CellType.values())
                .filter(cellType -> cellType.getTileId() == cellTypeId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private List<Item> createItemsFromData(List<Integer> itemsTileIds) {
        List<Item> items = new ArrayList<>();
        for (Integer itemsTileId : itemsTileIds) {
            Item item = MapLoader.createItem(null, Tiles.tileTypeMap.get(itemsTileId));
            items.add(item);
        }
        return items;
    }


    private List<CellModel> loadCells(int gameStateId) {
        return cellDao.getAllByGameStateId(gameStateId);
    }

    private Level loadGameState(int gameStateId) {
        GameState gameState = gameStateDao.get(gameStateId);
        return Arrays.stream(Level.values())
                .filter(level -> level.getID() == gameState.getLevelId())
                .findFirst()
                .orElseThrow();
    }

    public PlayerModel loadPlayer(int gameStateId) {
        return playerDao.get(gameStateId);
    }

    public List<CellModel> loadMap() {
        return null;
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
