package com.codecool.dungeoncrawl.logic.items;

public enum KeyType {
    DOOR_KEY("door_key"),
    CHEST_KEY("chest_key"),
    LEVEL_KEY("level_key");

    private final String tileName;

    KeyType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
