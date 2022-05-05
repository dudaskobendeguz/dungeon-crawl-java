package com.codecool.dungeoncrawl;

public enum TileCategory {
    CELL(true),
    PLAYER(false),
    MONSTER(false),
    ITEM(false),
    CHEST(true),
    TIME_CELL(true);
    private final boolean isReloadAble;

    TileCategory(boolean isReloadAble) {
        this.isReloadAble = isReloadAble;
    }

    public boolean isReloadAble() {
        return isReloadAble;
    }
}
