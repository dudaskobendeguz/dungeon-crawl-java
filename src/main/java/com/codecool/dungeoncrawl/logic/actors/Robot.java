package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Robot extends Monster implements Movable {
    private int dx = 1;

    public Robot(Cell cell) {
        super(cell, 1, 10, false);
    }

    @Override
    public String getTileName() {
        return MonsterType.ROBOT.getTileName();
    }

    @Override
    public void move(int playerX, int playerY) {
        Cell nextCell = cell.getNeighbor(dx, 0);
        if (!GameMap.isValidStep(nextCell)) {
            dx *= -1;
        }
        stepOne(nextCell);
    }
}
