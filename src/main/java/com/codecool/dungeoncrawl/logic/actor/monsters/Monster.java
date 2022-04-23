package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actor.Actor;

import java.util.Random;

public abstract class Monster extends Actor {
    protected boolean isTurnBased;
    protected final MonsterType monsterType;

    public Monster(MonsterType monsterType, Cell cell, int health, int damage) {
        this(monsterType, cell, health, damage, true);
    }

    public Monster(MonsterType monsterType, Cell cell, int health, int damage, boolean isTurnBased) {
        super(cell, health, damage);
        this.monsterType = monsterType;
        this.isTurnBased = isTurnBased;
    }

    @Override
    public int getTileId() {
        return monsterType.getTileId();
    }

    public boolean isTurnBased() {
        return isTurnBased;
    }

    public void die() {
        cell.setActor(null);
        cell = null;
    }
}
