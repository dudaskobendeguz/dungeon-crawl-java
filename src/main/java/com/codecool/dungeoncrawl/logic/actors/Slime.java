package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.KeyType;

public class Slime extends Monster implements Movable {

    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 1;

    public Slime(Cell cell) {
        super(cell, 20, 1);
    }

    @Override
    public String getTileName() {
        return MonsterType.SLIME.getTileName();
    }

    @Override
    public void move(int playerX, int playerY) {
        setTimer();
        if (moveTimer == 0) {
            stepOne(MoveUtil.moveRandomly(cell));
        }
    }

    private void setTimer() {
        if (++moveTimer >= MOVE_TIMER_CEILING) {
            moveTimer = 0;
        }
    }

    @Override
    public void die() {
        cell.setActor(null);
        cell.setItem(new Key(cell, KeyType.CHEST_KEY));
        cell = null;
    }
}
