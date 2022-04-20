package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

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

    protected boolean isValidStep(Cell cell) {
        return cell != null && cell.getType() == CellType.FLOOR && cell.getActor() == null;
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
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
}
