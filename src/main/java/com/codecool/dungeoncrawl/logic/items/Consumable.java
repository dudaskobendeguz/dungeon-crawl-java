package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Consumable extends Item {
    private final ConsumableType consumableType;

    public Consumable(Cell cell, ConsumableType consumableType) {
        super(cell);
        this.consumableType = consumableType;
    }

    @Override
    public int getTileId() {
        return consumableType.getTileId();
    }

    public int getHealthModifier() {
        return consumableType.getHealthModifier();
    }

    public ConsumableType getConsumableType() {
        return consumableType;
    }
}
