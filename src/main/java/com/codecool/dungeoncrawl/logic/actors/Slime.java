package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Slime extends Monster {
    public Slime(Cell cell) {
        super(cell, 20, 1);
    }

    @Override
    public String getTileName() {
        return "slime";
    }

    @Override
    public void move(int playerX, int playerY) {
        moveRandomly();
    }
}
