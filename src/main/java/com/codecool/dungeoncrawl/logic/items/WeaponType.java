package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.TileType;

public enum WeaponType implements TileType {
    KNIFE(896, "Knife",  23, 2),
    SWORD(960, "Sword",  27, 5),
    AXE(970, "Axe",  26, 10),
    HAMMER(933, "Hammer",  60, 20),
    FIST(-999999,"Fist",  25, 1),
    MAGIC(379, "Magic",  120, 5);

    private final int ID;
    private final String name;
    private final int playerSkinTileId;
    private final int damage;

    WeaponType(int tileId, String name, int playerSkinTileId, int damage) {
        this.ID = tileId;
        this.name = name;
        this.playerSkinTileId = playerSkinTileId;
        this.damage = damage;
    }

    public int getTileId() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPlayerSkinTileId() {
        return playerSkinTileId;
    }

    public int getDamage() {
        return damage;
    }
}
