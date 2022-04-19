package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    KNIFE("knife", 2),
    SWORD("sword", 5),
    AXE("axe", 10),
    HAMMER("hammer", 20);

    private final String tileName;
    private final int damage;

    WeaponType(String tileName, int damage) {
        this.tileName = tileName;
        this.damage = damage;
    }

    public String getTileName() {
        return tileName;
    }

    public int getDamage() {
        return damage;
    }
}
