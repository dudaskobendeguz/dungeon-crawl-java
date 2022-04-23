package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.KeyType;

import java.util.ArrayList;
import java.util.List;

public class TimeMage extends Monster implements Movable {
    private int moveTimer = 0;
    private static final int MOVE_TIMER_CEILING = 20;
    List<Cell> timeCells;


    public TimeMage(Cell cell) {
        super(MonsterType.TIME_MAGE, cell, 500, 3, false);

    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        moveTimer = MoveUtil.setTimer(moveTimer, MOVE_TIMER_CEILING);
        if (moveTimer == 0) {
            stepOne(timeCells.get(MoveUtil.random.nextInt(timeCells.size())), true);
        }
    }

    @Override
    public int getTileId() {
        return monsterType.getTileId();
    }

    public void setTimeCells(List<Cell> timeCells) {
        this.timeCells = timeCells;
    }

    public Monster attack() {
        if (moveTimer == 0) {
            Cell randomNonDiagonalNeighbor = cell.getNeighbor(MoveUtil.getRandomDirection());
            if (randomNonDiagonalNeighbor.getActor() == null && randomNonDiagonalNeighbor.isStepable()) {
                switch (MonsterType.values()[MoveUtil.random.nextInt(MonsterType.values().length)]) {
                    case ROBOT:
                        Robot robot = new Robot(randomNonDiagonalNeighbor);
                        randomNonDiagonalNeighbor.setActor(robot);
                        return robot;

                    case SLIME:
                        Slime slime = new Slime(randomNonDiagonalNeighbor);
                        randomNonDiagonalNeighbor.setActor(slime);
                        return slime;

                    case CHICKEN:
                        Chicken chicken = new Chicken(randomNonDiagonalNeighbor);
                        randomNonDiagonalNeighbor.setActor(chicken);
                        return chicken;

                    case SKELETON:
                        Skeleton skeleton = new Skeleton(randomNonDiagonalNeighbor);
                        randomNonDiagonalNeighbor.setActor(skeleton);
                        return skeleton;
                    default:
                }
            }
        }
        return null;
    }

    @Override
    public void die() {
        Cell nextCell = cell.getNeighbor(Direction.UP);
        nextCell.setItem(new Key(nextCell, KeyType.LEVEL_KEY));
        super.die();
    }
}
