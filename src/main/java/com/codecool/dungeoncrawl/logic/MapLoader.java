package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.TileType;
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
    private static final int PLAYER_ID = 25;

    public static GameMap loadMap(String filePath, Player player) {
        Scanner scanner = loadMapFile(filePath);
        String firstLine = scanner.nextLine();
        String[] firstLineChars = firstLine.split(DELIMITER);

        int width = firstLineChars.length;
        int height = 1;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            height++;
        }

        scanner = loadMapFile(filePath);

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        List<Cell> timeCells = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            String[] lineChars = line.split(DELIMITER);
            for (int x = 0; x < lineChars.length; x++) {
                Cell cell = map.getCell(x, y);
                int tileId = Integer.parseInt(lineChars[x]);
                TileType tileType = Tiles.tileTypeMap.get(tileId);

                if (tileId == PLAYER_ID) {
                    player.setCell(cell);
                    cell.setType(DEFAULT_CELL);
                    cell.setActor(player);
                    map.setPlayer(player);
                }
                else if (tileType == CellType.CHEST_CLOSED) {
                    map.setChest(x, y);
                } else if (tileType instanceof CellType) {
                    timeCells = setCell(cell, (CellType) tileType, timeCells);
                }
                else if (tileType instanceof MonsterType) {
                    setMonster(cell, map, (MonsterType) tileType);
                }
                else if (tileType instanceof ConsumableType) {
                    setItem(cell, new Consumable(cell, (ConsumableType) tileType));
                } else if (tileType instanceof KeyType) {
                    setItem(cell, new Key(cell, (KeyType) tileType));
                } else if (tileType instanceof WeaponType) {
                    setItem(cell, new Weapon(cell, (WeaponType) tileType));
                }
                else {
                    throw new RuntimeException("Unrecognized character: '" + lineChars[x] + "'");
                }
            }
        }
        if (timeCells.size() > 0) {
            for (Monster monster : map.getMonsters()) {
                if (monster instanceof TimeMage) {
                    ((TimeMage) monster).setTimeCells(timeCells);
                }
            }
        }
        return map;
    }

    private static Scanner loadMapFile(String filePath) {
        InputStream is = MapLoader.class.getResourceAsStream(filePath);
        assert is != null;
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter(DELIMITER);
        return scanner;
    }

    private static List<Cell> setCell(Cell cell, CellType cellType, List<Cell> timeCells) {
        cell.setType(cellType);
        if (cellType == CellType.TIME_MAGE_FLOOR) {
            timeCells.add(cell);
        }
        return timeCells;
    }

    private static void setMonster(Cell cell, GameMap map, MonsterType monsterType) {
        cell.setType(CellType.FLOOR_1);
        Monster monster = null;
        switch (monsterType) {
            case SKELETON:
                monster = new Skeleton(cell);
                break;
            case SLIME:
                monster = new Slime(cell);
                break;
            case CHICKEN:
                monster = new Chicken(cell);
                break;
            case ROBOT:
                monster = new Robot(cell);
                break;
            case TIME_MAGE:
                monster = new TimeMage(cell);
                break;
        }
        map.addMonster(monster);
    }

    private static void setItem(Cell cell, Item item) {
        cell.setType(DEFAULT_CELL);
        cell.setItem(item);
    }

}
