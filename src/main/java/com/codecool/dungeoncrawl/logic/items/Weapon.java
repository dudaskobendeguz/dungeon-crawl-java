package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {
    private final WeaponType weaponType;

    public Weapon(Cell cell, WeaponType weaponType) {
        super(cell);
        this.weaponType = weaponType;
    }

    public int getDamage() {
        return weaponType.getDamage();
    }

    public int getPlayerSkin() {
        return weaponType.getPlayerSkinTileId();
    }

    @Override
    public int getTileId() {
        return weaponType.getTileId();
    }

    public WeaponType getType() {
        return weaponType;
    }


    public String getNam() {
        return weaponType.getName();
    }
}
