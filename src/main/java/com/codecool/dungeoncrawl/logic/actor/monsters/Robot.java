package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Direction;

public class Robot extends Monster implements Movable {
    Direction direction = Direction.RIGHT;

    public Robot(Cell cell) {
        super(MonsterType.ROBOT, cell, 10, 2, false);
    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        if (cell != null) {
            Cell nextCell = cell.getNeighbor(direction);
            if (!GameMap.isStepValid(nextCell)) {
                direction = (direction.equals(Direction.RIGHT)) ? Direction.LEFT : Direction.RIGHT;
            }
            stepOne(nextCell);
        }
    }
}
