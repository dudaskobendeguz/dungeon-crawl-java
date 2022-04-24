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
    private static final String delimiter = ",";
    private static final CellType defaultCell = CellType.FLOOR_1;
    public static GameMap loadMap(String filePath, Player player) {
        Scanner scanner = loadMapFile(filePath);
        String firstLine = scanner.nextLine();
        String[] firstLineChars = firstLine.split(delimiter);

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
            String[] lineChars = line.split(delimiter);
            for (int x = 0; x < lineChars.length; x++) {
                Cell cell = map.getCell(x, y);
                int tileId = Integer.parseInt(lineChars[x]);
                TileType tileType = Tiles.tileTypeMap.get(tileId);
                if (tileId == 25) {
                    cell.setType(CellType.FLOOR_1);
                    player.setCell(cell);
                    cell.setActor(player);
                    map.setPlayer(player);
                } else if (tileType == MonsterType.TIME_MAGE) {
                    cell.setType(CellType.TIME_MAGE_FLOOR);
                    timeCells.add(cell);
                    TimeMage timeMage = new TimeMage(cell);
                    map.addMonster(timeMage);
                } else if (tileType == CellType.TIME_MAGE_FLOOR) {
                    cell.setType(CellType.TIME_MAGE_FLOOR);
                    timeCells.add(cell);
                } else if (tileType instanceof CellType) {
                    cell.setType((CellType) tileType);
                } else if (tileType instanceof MonsterType) {
                    setMonster(cell, map, (MonsterType) tileType);
                } else if (tileType instanceof ConsumableType) {
                    cell.setType(defaultCell);
                    cell.setItem(new Consumable(cell, (ConsumableType) tileType));
                } else if (tileType instanceof KeyType) {
                    cell.setType(defaultCell);
                    cell.setItem(new Key(cell, (KeyType) tileType));
                } else if (tileType instanceof WeaponType) {
                    cell.setType(defaultCell);
                    cell.setItem(new Weapon(cell, (WeaponType) tileType));
                } else {
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
        scanner.useDelimiter(delimiter);
        return scanner;
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
        }
        map.addMonster(monster);
    }

}
