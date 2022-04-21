package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;

    private Player player;
    private final List<Monster> monsters = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public static boolean isValidStep(Cell cell) {
        return cell != null && cell.getType().isStepable() && cell.getActor() == null;
    }

    public Cell getCell(int x, int y) {
        return (x >= 0 && x < cells.length && y >= 0 && y < cells[x].length) ? cells[x][y] : null;
    }

    public void setChest(int x, int y) {
        cells[x][y] = new Chest(this, x,y, CellType.CHEST_CLOSED);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }
}
