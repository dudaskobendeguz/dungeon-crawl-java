package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int damage;

    public Actor(Cell cell, int health, int damage) {
        this.cell = cell;
        this.health = health;
        this.damage = damage;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (isValidStep(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    private boolean isValidStep(Cell cell) {
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
