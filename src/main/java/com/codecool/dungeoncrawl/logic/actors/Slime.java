package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Slime extends Monster implements Movable {

    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 1;

    public Slime(Cell cell) {
        super(cell, 20, 1);
    }

    @Override
    public String getTileName() {
        return "slime";
    }

    @Override
    public void move(int playerX, int playerY) {
        setTimer();
        if (moveTimer == 0) {
            moveRandomly();
        }
    }

    private void setTimer() {
        if (++moveTimer >= MOVE_TIMER_CEILING) {
            moveTimer = 0;
        }
    }
}
