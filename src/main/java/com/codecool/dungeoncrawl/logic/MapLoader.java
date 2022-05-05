package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.Level;
import com.codecool.dungeoncrawl.TileType;
import com.codecool.dungeoncrawl.TileCategory;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.actor.monsters.*;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    private static final String DELIMITER = ",";
    private static final CellType DEFAULT_CELL = CellType.FLOOR_1;

    public static GameMap getGameMap(Level level, Player player) {
        GameMap gameMap = createGameMap(level, player, false);
        setTimeMageCells(gameMap);
        return gameMap;
    }

    public static GameMap getGameMap(GameStateModel gameStateModel) {
        Level level = gameStateModel.getLevel();
        GameMap gameMap = createGameMap(level, null, true);
        setPlayerModel(gameMap, gameStateModel.getPlayerModel());
        setCellModels(gameMap, gameStateModel.getCellModels());
        setMonsterModels(gameMap, gameStateModel.getMonsterModels());
        setItemModels(gameMap, gameStateModel.getItemModels());
        setTimeMageCells(gameMap);
        return gameMap;
    }

    private static void setItemModels(GameMap gameMap, List<ItemModel> itemModels) {
        for (ItemModel itemModel : itemModels) {
            TileType tileType = Tiles.tileTypeMap.get(itemModel.getTileId());
            Cell itemCell = gameMap.getCell(itemModel.getX(), itemModel.getY());
            itemCell.setItem(createItem(itemCell, tileType));
        }
    }

    private static void setMonsterModels(GameMap gameMap, List<MonsterModel> monsterModels) {
        for (MonsterModel monsterModel : monsterModels) {
            createMonster(gameMap, monsterModel);
        }
    }

    private static void setCellModels(GameMap gameMap, List<CellModel> cellModels) {
        for (CellModel cellModel : cellModels) {
            CellType cellType = (CellType) Tiles.tileTypeMap.get(cellModel.getTypeId());
            Cell cell = gameMap.getCell(cellModel.getX(), cellModel.getY());
            cell.setType(cellType);
        }
    }

    private static void setPlayerModel(GameMap gameMap, PlayerModel playerModel) {
        Cell playerCell = gameMap.getCell(playerModel.getX(), playerModel.getY());
        Player player = createNewPlayer(playerModel, playerCell);
        createPlayer(player, playerCell, gameMap);
    }

    private static Player createNewPlayer(PlayerModel playerModel, Cell playerCell) {
        List<Item> items = createItemsFromData(playerModel.getItems(), null);
        Weapon weapon = createWeaponFromData(playerModel.getWeaponTypeId());
        Direction direction = createDirectionFromData(playerModel.getDirectionTypeId());
        return new Player(playerModel, playerCell, direction, weapon, items);
    }

    private static Direction createDirectionFromData(int directionTypeId) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.getID() == directionTypeId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Weapon createWeaponFromData(int weaponTypeId) {
        WeaponType weaponType = (WeaponType) Tiles.tileTypeMap.get(weaponTypeId);
        return new Weapon(null, weaponType);
    }

    public static List<Item> createItemsFromData(List<Integer> itemsTileIds, Cell cell) {
        List<Item> items = new ArrayList<>();
        for (Integer itemsTileId : itemsTileIds) {
            Item item = MapLoader.createItem(cell, Tiles.tileTypeMap.get(itemsTileId));
            items.add(item);
        }
        return items;
    }

    private static GameMap createGameMap(Level level, Player player, boolean isLoading) {
        Scanner mapSizeScanner = loadMapFile(level.getMAP_FILE_PATH());
        int width = getMapWidth(mapSizeScanner);
        int height = getMapHeight(mapSizeScanner);

        GameMap map = new GameMap(width, height, CellType.EMPTY, level);
        Scanner mapScanner = loadMapFile(level.getMAP_FILE_PATH());
        loadTiles(map, mapScanner, player, isLoading);
        return map;
    }

    private static void loadTiles(GameMap map, Scanner mapScanner, Player player, boolean isLoading) {
        // TODO Remove player parameter from this scenario
        List<Cell> timeCells = new ArrayList<>();
        for (int y = 0; y < map.getHeight(); y++) {
            String line = mapScanner.nextLine();
            String[] lineChars = line.split(DELIMITER);
            for (int x = 0; x < map.getWidth(); x++) {
                Cell cell = map.getCell(x, y);
                int tileId = Integer.parseInt(lineChars[x]);
                TileType tileType = Tiles.tileTypeMap.get(tileId);
                TileCategory tileCategory = getTileCategory(tileId, tileType);
                if (isLoading) {
                    if (tileCategory.isReloadAble()) {
                        cell.setType((CellType) tileType);
                        if (tileCategory == TileCategory.TIME_CELL) {
                            timeCells.add(cell);
                        }
                    } else if (tileType == MonsterType.TIME_MAGE) {
                        cell.setType(CellType.TIME_MAGE_FLOOR);
                        timeCells.add(cell);
                    } else {
                        cell.setType(DEFAULT_CELL);
                    }
                } else {
                    switch (tileCategory) {
                        case CELL:
                            cell.setType((CellType) tileType);
                            break;
                        case PLAYER:
                            setPlayer(player, map, cell);
                            break;
                        case MONSTER:
                            setMonster(cell, map, (MonsterType) tileType);
                            break;
                        case ITEM:
                            setItem(cell, tileType);
                            break;
                        case CHEST:
                            map.setChest(x, y);
                            break;
                        case TIME_CELL:
                            cell.setType((CellType) tileType);
                            timeCells.add(cell);
                            break;
                    }
                }
            }
        }
        map.setTimeCells(timeCells);
    }

    private static Scanner loadMapFile(String filePath) {
        InputStream is = MapLoader.class.getResourceAsStream(filePath);
        assert is != null;
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter(DELIMITER);
        return scanner;
    }

    private static int getMapWidth(Scanner scanner) {
        String firstLine = scanner.nextLine();
        String[] firstLineChars = firstLine.split(DELIMITER);
        return firstLineChars.length;
    }

    private static int getMapHeight(Scanner scanner) {
        int height = 1;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            height++;
        }
        return height;
    }

    private static TileCategory getTileCategory(int tileId, TileType tileType) {
        // TODO Think about putting PLAYER and CHEST somewhere
        final int PLAYER_ID = 25;
        final int CHEST_ID = 200;
        final int TIME_MAGE_FLOOR = 588;
        switch (tileId) {
            case PLAYER_ID:
                return TileCategory.PLAYER;
            case CHEST_ID:
                return TileCategory.CHEST;
            case TIME_MAGE_FLOOR:
                return TileCategory.TIME_CELL;
            default:
                if (tileType == null) throw new RuntimeException("Unrecognized character: '" + tileId + "'");
                return tileType.getTileCategory();
        }
    }

    private static void setPlayer(Player player, GameMap map, Cell cell) {
        cell.setType(DEFAULT_CELL);
        createPlayer(player, cell, map);
    }

    private static void createPlayer(Player player, Cell cell, GameMap map) {
        player.setCell(cell);
        cell.setActor(player);
        map.setPlayer(player);
    }

    private static void setMonster(Cell cell, GameMap map, MonsterType monsterType) {
        cell.setType(DEFAULT_CELL);
        createMonster(cell, map, monsterType);
    }

    private static void createMonster(Cell cell, GameMap map, MonsterType monsterType) {
        Monster monster = MonsterType.getMonsterByMonsterType(monsterType, cell);
        if (monster instanceof Robot) {
            ((Robot) monster).setMoveDirection(Direction.RIGHT);
        }
        addMonsterToGameMap(cell, map, monster);
    }

    private static void createMonster(GameMap map, MonsterModel monsterModel) {
        TileType tileType = Tiles.tileTypeMap.get(monsterModel.getTypeId());
        Cell monsterCell = map.getCell(monsterModel.getX(), monsterModel.getY());
        Monster monster = MonsterType.getMonsterByMonsterType((MonsterType) tileType, monsterCell);
        if (monster instanceof MoveDirection) {
            ((MoveDirection) monster).setMoveDirection(monsterModel.getDirection());
        }
        addMonsterToGameMap(monsterCell, map, monster);
    }

    private static void addMonsterToGameMap(Cell cell, GameMap map, Monster monster) {
        setTimeMageFloor(cell, monster);
        map.addMonster(monster);
    }

    private static void setTimeMageFloor(Cell cell, Monster monster) {
        if (monster instanceof TimeMage) {
            cell.setType(CellType.TIME_MAGE_FLOOR);
        }
    }

    private static void setItem(Cell cell, TileType tileType) {
        cell.setType(DEFAULT_CELL);
        cell.setItem(createItem(cell, tileType));
    }

    public static Item createItem(Cell cell, TileType tileType) {
        // TODO Think about ItemType
        if (tileType instanceof ConsumableType) {
            return new Consumable(cell, (ConsumableType) tileType);
        }
        if (tileType instanceof KeyType) {
            return new Key(cell, (KeyType) tileType);
        }
        if (tileType instanceof WeaponType) {
            return new Weapon(cell, (WeaponType) tileType);
        }
        throw new RuntimeException("Cannot create item. TileType: " + tileType);
    }

    private static void setTimeMageCells(GameMap map) {
        List<Cell> timeCells = map.getTimeCells();
        if (timeCells != null && timeCells.size() > 0) {
            for (Monster monster : map.getMonsters()) {
                if (monster instanceof TimeMage) {
                    ((TimeMage) monster).setTimeCells(timeCells);
                }
            }
        }
    }


}
