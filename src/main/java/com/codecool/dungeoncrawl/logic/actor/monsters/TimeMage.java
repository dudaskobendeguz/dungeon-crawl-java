package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class TimeMage extends Monster implements Movable {
    List<Cell> timeCells = new ArrayList<>();


    public TimeMage(Cell cell) {
        super(cell, 10, 3, false);

    }

    @Override
    public void move(int playerX, int playerY) {
        stepOne(timeCells.get(MoveUtil.random.nextInt(timeCells.size())));
    }

    @Override
    protected void stepOne(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    @Override
    public String getTileName() {
        return MonsterType.TIME_MAGE.getTileName();
    }

    public void setTimeCells(List<Cell> timeCells) {
        this.timeCells = timeCells;
    }
}
