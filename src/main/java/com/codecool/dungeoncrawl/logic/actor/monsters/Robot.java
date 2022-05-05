package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Direction;

public class Robot extends Monster implements Movable, MoveDirection {

    Direction moveDirection;

    public Robot(Cell cell) {
        super(MonsterType.ROBOT, cell, 10, 2, false);
    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        if (cell != null) {
            Cell nextCell = cell.getNeighbor(moveDirection);
            if (!GameMap.isStepValid(nextCell)) {
                moveDirection = (moveDirection.equals(Direction.RIGHT)) ? Direction.LEFT : Direction.RIGHT;
            }
            stepOne(nextCell);
        }
    }

    @Override
    public Direction getMoveDirection() {
        return moveDirection;
    }

    @Override
    public void setMoveDirection(Direction moveDirection) {
        if (this.moveDirection == null) {
            this.moveDirection = moveDirection;
        }
    }
}
