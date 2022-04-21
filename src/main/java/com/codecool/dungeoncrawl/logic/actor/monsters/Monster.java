package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actor.Actor;

import java.util.Random;

public abstract class Monster extends Actor {
    private final boolean isTurnBased;

    public Monster(Cell cell, int health, int damage) {
        super(cell, health, damage);
        this.isTurnBased = true;
    }

    public Monster(Cell cell, int health, int damage, boolean isTurnBased) {
        super(cell, health, damage);
        this.isTurnBased = isTurnBased;
    }

    public boolean isTurnBased() {
        return isTurnBased;
    }

    public void die() {
        cell.setActor(null);
        cell = null;
    }
}
