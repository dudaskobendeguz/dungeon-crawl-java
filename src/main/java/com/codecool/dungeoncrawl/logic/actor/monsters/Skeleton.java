package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Skeleton extends Monster {
    public Skeleton(Cell cell) {
        super(cell, 2, 1);
    }

    @Override
    public String getTileName() {
        return MonsterType.SKELETON.getTileName();
    }

    @Override
    public void die() {
        cell.setType(CellType.BONES);
        super.die();
    }
}
