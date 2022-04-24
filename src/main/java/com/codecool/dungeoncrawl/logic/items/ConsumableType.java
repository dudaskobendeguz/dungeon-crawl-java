package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.TileType;

public enum ConsumableType implements TileType {
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
