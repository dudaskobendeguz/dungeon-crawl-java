package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public abstract class MoveUtil {
    static Random random = new Random();


    protected static Cell moveTowardsPlayer(Cell cell, int playerX, int playerY) {
        int x = cell.getX();
        int y = cell.getY();

        if (x > playerX) {
            return cell.getNeighbor(-1, 0);
        } else if (x < playerX) {
            return cell.getNeighbor(1, 0);
        }

        if (y > playerY) {
            return cell.getNeighbor(0, -1);
        } else if (y < playerY) {
            return cell.getNeighbor(0, 1);
        }
        return null;
    }

    protected static Cell moveRandomly(Cell cell) {
        int maxRandom = 3;
        int randomNumber = random.nextInt(maxRandom + 1);
        switch (randomNumber) {
            case 0:
                return cell.getNeighbor(1, 0);
            case 1:
                return cell.getNeighbor(-1, 0);
            case 2:
                return cell.getNeighbor(0, 1);
            case 3:
                return cell.getNeighbor(0, -1);
            default:
                return null;
        }
    }
}
