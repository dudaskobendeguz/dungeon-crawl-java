package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;

import java.util.Random;

abstract class MoveUtil {
    static Random random = new Random();


    static Cell moveTowardsPlayer(Cell cell, int playerX, int playerY) {
        int x = cell.getX();
        int y = cell.getY();

        if (x > playerX) {
            return cell.getNeighbor(Direction.LEFT);
        } else if (x < playerX) {
            return cell.getNeighbor(Direction.RIGHT);
        }

        if (y > playerY) {
            return cell.getNeighbor(Direction.UP);
        } else if (y < playerY) {
            return cell.getNeighbor(Direction.DOWN);
        }
        return null;
    }

    static Cell moveRandomly(Cell cell) {
        return cell.getNeighbor(Direction.values()[random.nextInt(Direction.values().length)]);
    }
}
