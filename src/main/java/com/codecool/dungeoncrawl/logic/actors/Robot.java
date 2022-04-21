package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Robot extends Monster implements Movable {
    public Robot(Cell cell) {
        super(cell, 1, 10, false);
    }

    @Override
    public String getTileName() {
        return "robot";
    }

    @Override
    public void move(int playerX, int playerY) {
        moveRandomly();
    }
}
