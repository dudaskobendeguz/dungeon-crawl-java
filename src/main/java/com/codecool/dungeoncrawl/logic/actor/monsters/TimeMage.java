package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;

public class TimeMage extends Monster implements Movable{

    public TimeMage(Cell cell) {
        super(cell, 10, 3);

    }

    @Override
    public void move(int playerX, int playerY) {

    }

    @Override
    public String getTileName() {
        return MonsterType.TIME_MAGE.getTileName();
    }
}
