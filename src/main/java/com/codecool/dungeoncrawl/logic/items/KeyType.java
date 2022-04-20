package com.codecool.dungeoncrawl.logic.items;

public enum KeyType {
    SIMPLE_DOOR_KEY("Door key", "simple_door_key"),
    CHEST_KEY("Chest key", "chest_key"),
    LEVEL_KEY("Level key", "level_key");

    private final String name;
    private final String tileName;

    KeyType(String name, String tileName) {
        this.name = name;
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

    @Override
    public String toString() {
        return name;
    }
}
