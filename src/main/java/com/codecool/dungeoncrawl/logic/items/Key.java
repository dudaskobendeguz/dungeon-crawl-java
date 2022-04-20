package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Key extends Item {
    private final KeyType keyType;

    public Key(Cell cell, KeyType keyType) {
        super(cell);
        this.keyType = keyType;
    }

    @Override
    public String getTileName() {
        return keyType.getTileName();
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public CellType getClosedDoorType() {
        return keyType.getClosedDoorType();
    }

    public CellType getOpenedDoor() {
        return keyType.getOpenedDoor();
    }
}
