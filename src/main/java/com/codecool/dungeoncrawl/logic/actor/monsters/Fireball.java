package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;

public class Fireball extends Monster implements Movable, MoveDirection {

    private Direction moveDirection;

    public Fireball(Cell cell) {
        super(MonsterType.FIREBALL, cell, 1, 100, false);
    }

    public Fireball(Cell cell, Direction moveDirection) {
        this(cell);
        this.moveDirection = moveDirection;
    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        tryToKill();
        Cell nextCell = cell.getNeighbor(moveDirection);
        if (!GameMap.isStepValid(nextCell)) {
            die();
        } else {
            stepOne(nextCell);
        }
    }

    public void tryToKill() {
        List<Monster> monsters = getNeighborMonsters();
        for (Monster monster : monsters) {
            killMonster(monster);
        }
    }

    public void killMonster(Monster monster) {
        monster.takeDamage(damage);
        if (monster.isAboutToDie()) {
            monster.die();
        }
    }

    @Override
    public Direction getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(Direction moveDirection) {
        if (this.moveDirection == null) {
            this.moveDirection = moveDirection;
        }
    }
}
