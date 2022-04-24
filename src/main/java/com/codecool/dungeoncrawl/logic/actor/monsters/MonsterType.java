package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.TileType;

public enum MonsterType implements TileType {
    SKELETON(93),
    CHICKEN(250),
    SLIME(283),
    ROBOT(122),
    TIME_MAGE(88),
    FIREBALL(335);

    private final int ID;

    MonsterType(int tileId) {
        this.ID = tileId;
    }

    public int getTileId() {
        return ID;
    }
}
