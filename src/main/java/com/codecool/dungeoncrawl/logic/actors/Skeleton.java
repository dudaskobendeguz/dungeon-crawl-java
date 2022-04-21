package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Monster {
    public Skeleton(Cell cell) {
        super(cell, 10, 2);
    }

    @Override
    public String getTileName() {
        return MonsterType.SKELETON.getTileName();
    }
}
