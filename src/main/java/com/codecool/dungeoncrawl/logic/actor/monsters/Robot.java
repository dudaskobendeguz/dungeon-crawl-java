package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Direction;

public class Robot extends Monster implements Movable {
    Direction direction = Direction.RIGHT;

    public Robot(Cell cell) {
        super(cell, 1, 10, false);
    }

    @Override
    public String getTileName() {
        return MonsterType.ROBOT.getTileName();
    }

    @Override
    public void move(int playerX, int playerY) {
        Cell nextCell = cell.getNeighbor(direction);
        if (!GameMap.isValidStep(nextCell)) {
            direction = (direction.equals(Direction.RIGHT)) ? Direction.DOWN : Direction.RIGHT;
        }
        stepOne(nextCell);
    }
}
