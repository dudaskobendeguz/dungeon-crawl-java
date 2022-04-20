package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public abstract class Monster extends Actor {
    public Monster(Cell cell, int health, int damage) {
        super(cell, health, damage);
    }

    public abstract void move();

    public void die() {

    }
}
