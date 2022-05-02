package com.codecool.dungeoncrawl.logic.actor;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected int health;
    protected int damage;

    public Actor(Cell cell, int health, int damage) {
        this.cell = cell;
        this.health = health;
        this.damage = damage;
        this.cell.setActor(this);
    }

    public Actor(int health, int damage) {
        this.cell = null;
        this.health = health;
        this.damage = damage;
    }

    public boolean isAboutToDie() {
        return health <= 0;
    }

    public boolean isMonster() {
        return this instanceof Monster;
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getCellTitleId() {
        return cell.getTileId();
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    protected void stepOne(Cell nextCell) {
        stepOne(nextCell, true);
    }

    protected void stepOne(Cell nextCell, boolean hasToBeValid) {
        if (!hasToBeValid || GameMap.isStepValid(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    protected List<Monster> getNeighborMonsters() {
        List<Monster> monsters = new ArrayList<>();
        List<Cell> cells = cell.getNonDiagonalNeighbors();
        for (Cell cell : cells) {
            if (cell != null) {
                Actor actor = cell.getActor();
                if (actor != null && actor.isMonster()) {
                    monsters.add((Monster) actor);
                }
            }
        }
        return monsters;
    }
}
