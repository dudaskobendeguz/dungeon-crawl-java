package com.codecool.dungeoncrawl.logic.items;

public enum ConsumableType {
    APPLE("Apple", "apple", 1),
    BREAD("Bread", "bread", 2),
    MEAT("Meat", "meat", 3);

    private final String name;
    private final String tileName;
    private final int healthModifier;

    ConsumableType(String name, String tileName, int healthModifier) {
        this.name = name;
        this.tileName = tileName;
        this.healthModifier = healthModifier;
    }

    public int getHealthModifier() {
        return healthModifier;
    }

    public String getTileName() {
        return tileName;
    }

    @Override
    public String toString() {
        return name;
    }
}
