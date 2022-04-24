package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.TileType;
import com.codecool.dungeoncrawl.LoadableTile;

public enum ConsumableType implements LoadableTile {
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

    @Override
    public TileType getTileType() {
        return TileType.ITEM;
    }

    public String getName() {
        return name;
    }
}
