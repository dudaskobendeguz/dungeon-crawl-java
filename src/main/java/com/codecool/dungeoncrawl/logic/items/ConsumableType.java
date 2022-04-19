package com.codecool.dungeoncrawl.logic.items;

public enum ConsumableType {
    APPLE("apple", 5),
    BREAD("bread", 10),
    MEAT("meat", 20);

    private final String tileName;
    private final int healthModifier;

    ConsumableType(String tileName, int healthModifier) {
        this.tileName = tileName;
        this.healthModifier = healthModifier;
    }

    public int getHealthModifier() {
        return healthModifier;
    }

    public String getTileName() {
        return tileName;
    }
}
