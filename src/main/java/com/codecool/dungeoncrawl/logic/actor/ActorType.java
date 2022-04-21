package com.codecool.dungeoncrawl.logic.actor;

public enum ActorType {
    PLAYER("player"),
    SKELETON("skeleton"),
    ;
    private final String tileName;

    ActorType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
