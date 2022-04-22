package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;

import java.util.Random;

abstract class MoveUtil {
    static Random random = new Random();


    static Cell moveTowardsPlayer(Cell cell, int playerX, int playerY) {
        int x = cell.getX();
        int y = cell.getY();
        Cell step;
        if (x > playerX) {
            step = cell.getNeighbor(Direction.LEFT);
        } else if (x < playerX) {
            step = cell.getNeighbor(Direction.RIGHT);
        }

        else if (y > playerY) {
            step = cell.getNeighbor(Direction.UP);
        } else if (y < playerY) {
            step = cell.getNeighbor(Direction.DOWN);
        } else {
            step = moveRandomly(cell);
        }
        return (step.isStepable()) ? step : moveRandomly(cell);
    }

    static Cell moveRandomly(Cell cell) {
        return cell.getNeighbor(Direction.values()[random.nextInt(Direction.values().length)]);
    }

    static int setTimer(int moveTimer, int MOVE_TIMER_CEILING) {
        if (++moveTimer >= MOVE_TIMER_CEILING) {
            moveTimer = 0;
        }
        return moveTimer;
    }

    static boolean setIsTurnBased(boolean isTimeMageAlive) {
        return isTimeMageAlive;
    }

    static Direction getRandomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }
}
