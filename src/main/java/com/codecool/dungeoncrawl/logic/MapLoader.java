package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

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
                switch (lineChars[x]) {
                    case "-1":
                        cell.setType(CellType.EMPTY);
                        break;
                    case "0":
                        cell.setType(CellType.EMPTY);
                        break;
                    case "586":
                        cell.setType(CellType.WALL);
                        break;
                    case "32":
                        cell.setType(CellType.TREE);
                        break;
                    case "4":
                        cell.setType(CellType.FLOOR);
                        break;
                    case "93":
                        cell.setType(CellType.FLOOR);
                        new Skeleton(cell);
                        break;
                    case "27":
                        cell.setType(CellType.FLOOR);
                        map.setPlayer(new Player(cell));
                        break;
                    default:
                        throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                }
            }
        }
        return map;
    }

}
