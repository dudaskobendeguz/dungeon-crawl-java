package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actor.Actor;

public abstract class Monster extends Actor {
    public Monster(Cell cell, int health, int damage) {
        super(cell, health, damage);
    }

    public abstract void move(int playerX, int playerY);

    protected void moveTowardsPlayer(int playerX, int playerY) {
        int x = getX();
        int y = getY();
        if (x > playerX) {
            stepOne(cell.getNeighbor(-1, 0));
        } else if (x < playerX) {
            stepOne(cell.getNeighbor(1, 0));
        } else if (y > playerY) {
            stepOne(cell.getNeighbor(0, -1));
        } else if (y < playerY) {
            stepOne(cell.getNeighbor(0, 1));
        }
    }

    public void die() {
        cell.setActor(null);
        cell = null;
    }
}
