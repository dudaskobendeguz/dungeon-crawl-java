package com.codecool.dungeoncrawl.logic.items;

public enum ConsumableType {
    APPLE(943, "Apple", 1),
    BREAD(911, "Bread",  2),
    MEAT(913, "Meat",  3);

    private final int ID;
    private final String name;
    private final int healthModifier;

    ConsumableType(int id, String name, int healthModifier) {
        ID = id;
        this.name = name;
        this.healthModifier = healthModifier;
    }

    public int getHealthModifier() {
        return healthModifier;
    }

    public int getTileId() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
