package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;

public class Chicken extends Monster implements Movable, MoveTimer {

    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 3;

    public Chicken(Cell cell) {
        super(MonsterType.CHICKEN, cell, 2, 1);
    }

    @Override
    public void move(int playerX, int playerY, boolean isTimeMageAlive) {
        isTurnBased = MoveUtil.setIsTurnBased(isTimeMageAlive);
        if (!isTurnBased) {
            damage = 0;
        }
        moveTimer = MoveUtil.setTimer(moveTimer, MOVE_TIMER_CEILING);
        if (moveTimer == 0) {
            stepOne(MoveUtil.moveTowardsPlayer(cell, playerX, playerY));
        }
    }

    @Override
    public int getMoveTimer() {
        return moveTimer;
    }
}
