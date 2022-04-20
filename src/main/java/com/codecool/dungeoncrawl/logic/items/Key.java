package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

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
}
