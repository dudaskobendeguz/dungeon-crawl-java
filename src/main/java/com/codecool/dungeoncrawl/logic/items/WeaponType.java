package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    KNIFE("Knife", "knife", 2),
    SWORD("Sword", "sword", 5),
    AXE("Axe", "axe", 10),
    HAMMER("Hammer", "hammer", 20),
    FIST("Fist", "fist", 1);

    private final String name;
    private final String tileName;
    private final int damage;

    WeaponType(String name, String tileName, int damage) {
        this.name = name;
        this.tileName = tileName;
        this.damage = damage;
    }

    public String getTileName() {
        return tileName;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return name;
    }
}
