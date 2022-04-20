package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Chicken extends Monster {
    public Chicken(Cell cell) {
        super(cell, 2, 1);
    }

    @Override
    public String getTileName() {
        return "chicken";
    }

    @Override
    public void move(int playerX, int playerY) {
        moveTowardsPlayer(playerX, playerY);
    }
}
