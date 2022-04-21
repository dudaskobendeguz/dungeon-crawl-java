package com.codecool.dungeoncrawl.logic.actors;

public enum MonsterType {
    SKELETON("skeleton");

    private final String tileName;

    MonsterType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
