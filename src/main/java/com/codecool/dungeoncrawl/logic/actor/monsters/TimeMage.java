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
    List<Cell> timeCells = new ArrayList<>();


    public TimeMage(Cell cell) {
        super(cell, 500, 3, false);

    }

    @Override
    public void move(int playerX, int playerY) {
        moveTimer = MoveUtil.setTimer(moveTimer, MOVE_TIMER_CEILING);
        if (moveTimer == 0) {
            stepOne(timeCells.get(MoveUtil.random.nextInt(timeCells.size())));
        }
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

    public List<Monster> attack() {
        List<Monster> teleportedMonsters = new ArrayList<>();
        for (Cell nonDiagonalNeighbor : cell.getNonDiagonalNeighbors()) {
            if (nonDiagonalNeighbor.getActor() == null && nonDiagonalNeighbor.isStepable()) {
                switch (MonsterType.values()[MoveUtil.random.nextInt(MonsterType.values().length)]) {
                    case ROBOT:
                        Robot robot = new Robot(nonDiagonalNeighbor);
                        nonDiagonalNeighbor.setActor(robot);
                        teleportedMonsters.add(robot);
                        break;
                    case SLIME:
                        Slime slime = new Slime(nonDiagonalNeighbor);
                        nonDiagonalNeighbor.setActor(slime);
                        teleportedMonsters.add(slime);
                        break;
                    case CHICKEN:
                        Chicken chicken = new Chicken(nonDiagonalNeighbor);
                        nonDiagonalNeighbor.setActor(chicken);
                        teleportedMonsters.add(chicken);
                        break;
                    case SKELETON:
                        Skeleton skeleton = new Skeleton(nonDiagonalNeighbor);
                        nonDiagonalNeighbor.setActor(skeleton);
                        teleportedMonsters.add(skeleton);
                        break;
                    default:
                        break;
                }
                if (MoveUtil.random.nextInt(4) < 1) {
                    return teleportedMonsters;
                }
            }
        }
        return teleportedMonsters;
    }

    @Override
    public void die() {
        Cell nextCell = cell.getNeighbor(Direction.UP);
        nextCell.setItem(new Key(nextCell, KeyType.LEVEL_KEY));
        super.die();
    }
}
