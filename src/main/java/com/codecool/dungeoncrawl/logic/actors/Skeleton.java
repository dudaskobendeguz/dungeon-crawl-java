package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actor.ActorType;

public class Skeleton extends Monster {
    public Skeleton(Cell cell) {
        super(cell, 10, 2);
    }

    @Override
    public String getTileName() {
        return ActorType.SKELETON.getTileName();
    }

    @Override
    public void move() {
        // Skeleton doesn't move
    }
}
