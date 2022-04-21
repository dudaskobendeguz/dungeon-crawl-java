package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Robot extends Monster implements Movable {
    public Robot(Cell cell) {
        super(cell, 10, 10);
    }

    @Override
    public String getTileName() {
        return "robot";
    }

    @Override
    public void move(int playerX, int playerY) {

    }
}
