package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public abstract class MoveUtil {
    public static Cell moveTowardsPlayer(Cell cell, int playerX, int playerY) {
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
}
