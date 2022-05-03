package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.KeyType;

public class Slime extends Monster implements Movable, MoveTimer {

    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 1;

    public Slime(Cell cell) {
        super(MonsterType.SLIME, cell, 8, 1);
    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        isTurnBased = MoveUtil.setIsTurnBased(timeMageAlive);
        if (!isTurnBased) {
            damage = 0;
        }
        moveTimer = MoveUtil.setTimer(moveTimer, MOVE_TIMER_CEILING);
        if (moveTimer == 0) {
            stepOne(MoveUtil.moveRandomly(cell));
        }
    }

    @Override
    public void die() {
        cell.setItem(new Key(cell, KeyType.CHEST_KEY));
        super.die();
    }

    @Override
    public int getMoveTimer() {
        return moveTimer;
    }
}
