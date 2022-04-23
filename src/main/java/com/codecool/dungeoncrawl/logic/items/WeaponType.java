package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    KNIFE(896, "Knife",  23, 2),
    SWORD(960, "Sword",  27, 5),
    AXE(970, "Axe",  26, 10),
    HAMMER(933, "Hammer",  60, 20),
    FIST(-999999,"Fist",  25, 1),
    MAGIC(379, "Magic",  120, 5);

    private final int ID;
    private final String name;
    private final int playerSkinId;
    private final int damage;

    WeaponType(int tileId, String name, int playerSkin, int damage) {
        this.ID = tileId;
        this.name = name;
        this.playerSkinId = playerSkin;
        this.damage = damage;
    }

    public int getTileId() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPlayerSkinId() {
        return playerSkinId;
    }

    public int getDamage() {
        return damage;
    }
}
