package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    KNIFE("Knife", "knife", "knife_player_skin", 2),
    SWORD("Sword", "sword", "sword_player_skin", 5),
    AXE("Axe", "axe", "axe_player_skin", 10),
    HAMMER("Hammer", "hammer", "hammer_player_skin", 20),
    FIST("Fist", "fist", "fist_player_skin", 1),
    MAGIC("Magic", "magic", "magic_player_skin", 5);

    private final String name;
    private final String tileName;
    private final String playerSkin;
    private final int damage;

    WeaponType(String name, String tileName, String playerSkin, int damage) {
        this.name = name;
        this.tileName = tileName;
        this.playerSkin = playerSkin;
        this.damage = damage;
    }

    public String getTileName() {
        return tileName;
    }

    public String getPlayerSkin() {
        return playerSkin;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return name;
    }
}
