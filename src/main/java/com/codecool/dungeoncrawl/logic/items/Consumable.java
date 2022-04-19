package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Consumable extends Item {
    private final ConsumableType consumableType;

    public Consumable(Cell cell, ConsumableType consumableType) {
        super(cell);
        this.consumableType = consumableType;
    }

    @Override
    public String getTileName() {
        return consumableType.getTileName();
    }

    public int getHealthModifier() {
        return consumableType.getHealthModifier();
    }
}
