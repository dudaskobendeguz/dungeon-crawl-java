package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actor.Actor;

import java.util.Random;

public abstract class Monster extends Actor {
    private final boolean isTurnBased;
    protected Random random = new Random();


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

    protected void moveRandomly() {
        int maxRandom = 3;
        int randomNumber = random.nextInt(maxRandom + 1);
        switch (randomNumber) {
            case 0:
                stepOne(cell.getNeighbor(1, 0));
                break;
            case 1:
                stepOne(cell.getNeighbor(-1, 0));
                break;
            case 2:
                stepOne(cell.getNeighbor(0, 1));
                break;
            case 3:
                stepOne(cell.getNeighbor(0, -1));
                break;
        }
    }

    public void die() {
        cell.setActor(null);
        cell = null;
    }
}
