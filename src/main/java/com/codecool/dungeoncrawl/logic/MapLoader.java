package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        String delimiter = ",";
        InputStream is = MapLoader.class.getResourceAsStream("/custom_map/level_1.csv");
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter(delimiter);
        String firstLine = scanner.nextLine();
        String[] firstLineChars = firstLine.split(delimiter);
        int width = firstLineChars.length;
        int height = 1;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            height++;
        }

        is = MapLoader.class.getResourceAsStream("/custom_map/level_1.csv");
        scanner = new Scanner(is);
        scanner.useDelimiter(",");

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            String[] lineChars = line.split(delimiter);
            for (int x = 0; x < lineChars.length; x++) {
                Cell cell = map.getCell(x, y);
                switch (Integer.parseInt(lineChars[x])) {
                    case -1:
                        cell.setType(CellType.EMPTY);
                        break;
                    case 0:
                        cell.setType(CellType.EMPTY);
                        break;
                    case 586:
                        cell.setType(CellType.WALL);
                        break;
                    case 32:
                        cell.setType(CellType.TREE);
                        break;
                    case 4:
                        cell.setType(CellType.FLOOR);
                        break;
                    case 93:
                        cell.setType(CellType.FLOOR);
                        Skeleton skeleton = new Skeleton(cell);
                        map.addMonster(skeleton);
                        break;
                    case 27:
                        cell.setType(CellType.FLOOR);
                        map.setPlayer(new Player(cell));
                        break;
                    case 896:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Weapon(cell, WeaponType.KNIFE));
                        break;
                    case 960:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Weapon(cell, WeaponType.SWORD));
                        break;
                    case 970:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Weapon(cell, WeaponType.AXE));
                        break;
                    case 933:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Weapon(cell, WeaponType.HAMMER));
                        break;
                    case 913:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Consumable(cell, ConsumableType.MEAT));
                        break;
                    case 911:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Consumable(cell, ConsumableType.BREAD));
                        break;
                    case 943:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Consumable(cell, ConsumableType.APPLE));
                        break;
                    case 754:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Key(cell, KeyType.LEVEL_KEY));
                        break;
                    case 753:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Key(cell, KeyType.DOOR_KEY));
                        break;
                    case 752:
                        cell.setType(CellType.FLOOR);
                        cell.setItem(new Key(cell, KeyType.CHEST_KEY));
                        break;
                    default:
                        throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                }
            }
        }
        return map;
    }

}
