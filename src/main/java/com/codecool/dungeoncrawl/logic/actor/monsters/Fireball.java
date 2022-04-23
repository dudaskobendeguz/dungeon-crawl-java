package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;

public class Fireball extends Monster implements Movable {

    private final Direction direction;

    public Fireball(Cell cell, Direction direction) {
        super(MonsterType.FIREBALL, cell, 1, 100, false);
        this.direction = direction;
    }

    @Override
    public int getTileId() {
        return monsterType.getTileId();
    }

    @Override
    public void move(int playerX, int playerY, boolean timeMageAlive) {
        tryToKill();
        Cell nextCell = cell.getNeighbor(direction);
        if (!GameMap.isValidStep(nextCell)) {
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
}
