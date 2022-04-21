package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actor.monsters.*;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    private static final String delimiter = ",";
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
                switch (Integer.parseInt(lineChars[x])) {
                    case -1:
                    case 0:
                        cell.setType(CellType.EMPTY);
                        break;
                    case 586:
                        cell.setType(CellType.WALL);
                        break;
                    case 449:
                        cell.setType(CellType.GRAVE_1);
                        break;
                    case 480:
                        cell.setType(CellType.BONES);
                        break;
                    case 485:
                        cell.setType(CellType.CANDLES);
                        break;
                    case 644:
                        cell.setType(CellType.HOUSE_1);
                        break;
                    case 647:
                        cell.setType(CellType.LEVEL_SWITCH_HOUSE);
                        break;
                    case 194:
                        cell.setType(CellType.LEVEL_SWITCH_STAIRS);
                        break;
                    case 673:
                        cell.setType(CellType.HOUSE_2);
                        break;
                    case 448:
                        cell.setType(CellType.GRAVE_2);
                        break;
                    case 326:
                        cell.setType(CellType.SIMPLE_DOOR_CLOSED);
                        break;
                    case 288:
                        cell.setType(CellType.LEVEL_SWITCH_DOOR_CLOSED);
                        break;
                    case 200:
                        map.setChest(x,y);
                        break;
                    case 32:
                        cell.setType(CellType.TREE_1);
                        break;
                    case 33:
                        cell.setType(CellType.TREE_2);
                        break;
                    case 34:
                        cell.setType(CellType.TREE_3);
                        break;
                    case 35:
                        cell.setType(CellType.TREE_4);
                        break;
                    case 36:
                        cell.setType(CellType.TREE_5);
                        break;
                    case 37:
                        cell.setType(CellType.TREE_6);
                        break;
                    case 64:
                        cell.setType(CellType.TREE_7);
                        break;
                    case 65:
                        cell.setType(CellType.TREE_8);
                        break;
                    case 66:
                        cell.setType(CellType.TREE_9);
                        break;
                    case 67:
                        cell.setType(CellType.TREE_10);
                        break;
                    case 68:
                        cell.setType(CellType.TREE_11);
                        break;
                    case 69:
                        cell.setType(CellType.TREE_12);
                        break;
                    case 5:
                        cell.setType(CellType.TREE_13);
                        break;
                    case 6:
                        cell.setType(CellType.TREE_14);
                        break;
                    case 7:
                        cell.setType(CellType.TREE_15);
                        break;
                    case 179:
                        cell.setType(CellType.TREE_16);
                        break;
                    case 180:
                        cell.setType(CellType.TREE_17);
                        break;
                    case 205:
                        cell.setType(CellType.TREE_18);
                        break;
                    case 206:
                        cell.setType(CellType.TREE_19);
                        break;
                    case 207:
                        cell.setType(CellType.TREE_20);
                        break;
                    case 208:
                        cell.setType(CellType.TREE_21);
                        break;
                    case 209:
                        cell.setType(CellType.TREE_22);
                        break;
                    case 210:
                        cell.setType(CellType.TREE_23);
                        break;
                    case 211:
                        cell.setType(CellType.TREE_24);
                        break;
                    case 212:
                        cell.setType(CellType.TREE_25);
                        break;
                    case 4:
                        cell.setType(CellType.FLOOR_1);
                        break;
                    case 16:
                        cell.setType(CellType.BRICK_FLOOR);
                        break;
                    case 93:
                        cell.setType(CellType.FLOOR_1);
                        Skeleton skeleton = new Skeleton(cell);
                        map.addMonster(skeleton);
                        break;
                    case 250:
                        cell.setType(CellType.FLOOR_1);
                        Chicken chicken = new Chicken(cell);
                        map.addMonster(chicken);
                        break;
                    case 283:
                        cell.setType(CellType.FLOOR_1);
                        Slime slime = new Slime(cell);
                        map.addMonster(slime);
                        break;
                    case 122:
                        cell.setType(CellType.FLOOR_1);
                        Robot robot = new Robot(cell);
                        map.addMonster(robot);
                        break;
                    case 88:
                        cell.setType(CellType.TIME_MAGE_FLOOR);
                        timeCells.add(cell);
                        TimeMage timeMage = new TimeMage(cell);
                        map.addMonster(timeMage);
                        break;
                    case 25:
                        cell.setType(CellType.FLOOR_1);
                        player.setCell(cell);
                        cell.setActor(player);
                        map.setPlayer(player);
                        break;
                    case 1:
                        cell.setType(CellType.FLOOR_2);
                        break;
                    case 588:
                        cell.setType(CellType.TIME_MAGE_FLOOR);
                        timeCells.add(cell);
                        break;
                    case 896:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Weapon(cell, WeaponType.KNIFE));
                        break;
                    case 960:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Weapon(cell, WeaponType.SWORD));
                        break;
                    case 970:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Weapon(cell, WeaponType.AXE));
                        break;
                    case 933:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Weapon(cell, WeaponType.HAMMER));
                        break;
                    case 913:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Consumable(cell, ConsumableType.MEAT));
                        break;
                    case 911:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Consumable(cell, ConsumableType.BREAD));
                        break;
                    case 943:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Consumable(cell, ConsumableType.APPLE));
                        break;
                    case 753:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Key(cell, KeyType.LEVEL_KEY));
                        break;
                    case 754:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Key(cell, KeyType.SIMPLE_DOOR_KEY));
                        break;
                    case 752:
                        cell.setType(CellType.FLOOR_1);
                        cell.setItem(new Key(cell, KeyType.CHEST_KEY));
                        break;
                    default:
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

}
