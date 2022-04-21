package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Chicken extends Monster implements Movable {

    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 3;

    public Chicken(Cell cell) {
        super(cell, 2, 1);
    }

    @Override
    public String getTileName() {
        return "chicken";
    }

    @Override
    public void move(int playerX, int playerY) {
        setTimer();
        if (moveTimer == 0) {
            moveTowardsPlayer(playerX, playerY);
        }
    }

    private void setTimer() {
        if (++moveTimer >= MOVE_TIMER_CEILING) {
            moveTimer = 0;
        }
    }
}
