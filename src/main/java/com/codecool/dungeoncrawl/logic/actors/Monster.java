package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actor.Actor;

public abstract class Monster extends Actor {
    public Monster(Cell cell, int health, int damage) {
        super(cell, health, damage);
    }

    public abstract void move();

    public void die() {
        cell.setActor(null);
        cell = null;
    }
}
