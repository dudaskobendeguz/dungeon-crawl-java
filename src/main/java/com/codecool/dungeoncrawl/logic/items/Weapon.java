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

    public String getPlayerSkin() {
        return weaponType.getPlayerSkin();
    }

    @Override
    public String getTileName() {
        return weaponType.getTileName();
    }

    public WeaponType getType() {
        return weaponType;
    }


    public String getNam() {
        return weaponType.getName();
    }
}
