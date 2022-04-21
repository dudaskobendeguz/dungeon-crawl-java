package com.codecool.dungeoncrawl.logic.actor.monsters;

public enum MonsterType {
    SKELETON("skeleton"),
    CHICKEN("chicken"),
    SLIME("slime"),
    ROBOT("robot");

    private final String tileName;

    MonsterType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
