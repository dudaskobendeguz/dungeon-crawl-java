package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.Level;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cellsMatrix;
    private Level level;

    private Player player;
    private final List<Monster> monsters = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCell, Level level) {
        this.width = width;
        this.height = height;
        this.level = level;
        cellsMatrix = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cellsMatrix[x][y] = new Cell(this, x, y, defaultCell);
            }
        }
    }

    public static boolean isStepValid(Cell cell) {
        return cell != null && cell.isStepable() && cell.getActor() == null;
    }

    public Cell getCell(int x, int y) {
        return (x >= 0 && x < cellsMatrix.length && y >= 0 && y < cellsMatrix[x].length) ? cellsMatrix[x][y] : null;
    }

    public void setChest(int x, int y) {
        if (getCell(x,y) != null) {
            cellsMatrix[x][y] = new Chest(this, x,y, CellType.CHEST_CLOSED);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerX() {
        return player.getX();
    }

    public int getPlayerY() {
        return player.getY();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Level getLevel() {
        return level;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (Cell[] cells : cellsMatrix) {
            for (Cell cell : cells) {
                Item item = cell.getItem();
                if (item != null) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }
}
