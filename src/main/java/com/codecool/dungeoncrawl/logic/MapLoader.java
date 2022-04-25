package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.TileType;
import com.codecool.dungeoncrawl.TileCategory;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.actor.monsters.*;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    private static final String DELIMITER = ",";
    private static final CellType DEFAULT_CELL = CellType.FLOOR_1;

    public static GameMap getGameMap(String filePath, Player player) {
        Scanner mapSizeScanner = loadMapFile(filePath);
        int width = getMapWidth(mapSizeScanner);
        int height = getMapHeight(mapSizeScanner);

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        Scanner mapScanner = loadMapFile(filePath);
        loadTiles(map, mapScanner, player, width, height);
        return map;
    }

    private static void loadTiles(GameMap map, Scanner mapScanner, Player player, int width, int height) {
        List<Cell> timeCells = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            String line = mapScanner.nextLine();
            String[] lineChars = line.split(DELIMITER);
            for (int x = 0; x < width; x++) {
                Cell cell = map.getCell(x, y);
                int tileId = Integer.parseInt(lineChars[x]);
                TileType tileType = Tiles.tileTypeMap.get(tileId);
                TileCategory tileCategory = getTileCategory(tileId, tileType);
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
        setTimeMageCells(map, timeCells);
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

    private  static int getMapHeight(Scanner scanner) {
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
        switch (tileId) {
            case PLAYER_ID:
                return TileCategory.PLAYER;
            case CHEST_ID:
                return TileCategory.CHEST;
            default:
                if (tileType == null) throw new RuntimeException("Unrecognized character: '" + tileId + "'");
                return tileType.getTileCategory();
        }
    }

    private static void setPlayer(Player player, GameMap map, Cell cell) {
        player.setCell(cell);
        cell.setType(DEFAULT_CELL);
        cell.setActor(player);
        map.setPlayer(player);
    }

    private static void setMonster(Cell cell, GameMap map, MonsterType monsterType) {
        cell.setType(DEFAULT_CELL);
        Monster monster = MonsterType.getMonsterByMonsterType(monsterType, cell);
        map.addMonster(monster);
    }

    private static void setItem(Cell cell, TileType tileType) {
        // TODO Think about ItemType
        cell.setType(DEFAULT_CELL);
        if (tileType instanceof ConsumableType) {
            cell.setItem(new Consumable(cell, (ConsumableType) tileType));
        } else if (tileType instanceof KeyType) {
            cell.setItem(new Key(cell, (KeyType) tileType));
        } else if (tileType instanceof WeaponType) {
            cell.setItem(new Weapon(cell, (WeaponType) tileType));
        }
    }

    private static void setTimeMageCells(GameMap map, List<Cell> timeCells) {
        if (timeCells.size() > 0) {
            for (Monster monster : map.getMonsters()) {
                if (monster instanceof TimeMage) {
                    ((TimeMage) monster).setTimeCells(timeCells);
                }
            }
        }
    }
}
