package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.WeaponType;

public class Fireball extends Monster implements Movable {

    private final Direction direction;

    public Fireball(Cell cell, Direction direction) {
        super(cell, 1, 100, false);
        this.direction = direction;
    }

    @Override
    public String getTileName() {
        return WeaponType.MAGIC.getTileName();
    }

    @Override
    public void move(int playerX, int playerY) {
        Cell nextCell = cell.getNeighbor(direction);
        if (!GameMap.isValidStep(nextCell)) {
            die();
        } else {
            stepOne(nextCell);
        }
    }
}
