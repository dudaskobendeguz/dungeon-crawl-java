package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.KeyType;

public class Slime extends Monster implements Movable {

    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 1;

    public Slime(Cell cell) {
        super(MonsterType.SLIME, cell, 8, 1);
    }

    @Override
    public int getTileId() {
        return monsterType.getTileId();
    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        isTurnBased = MoveUtil.setIsTurnBased(timeMageAlive);
        if (!isTurnBased) {
            damage = 0;
        }
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
        cell.setItem(new Key(cell, KeyType.CHEST_KEY));
        super.die();
    }
}
