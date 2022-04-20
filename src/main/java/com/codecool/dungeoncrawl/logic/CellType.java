package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", false),
    FLOOR("floor", true),
    TREE("tree", false),
    WALL("wall", false),
    SIMPLE_DOOR_OPENED("simple_door_opened", true),
    SIMPLE_DOOR_CLOSED("simple_door_closed", false);

    private final String tileName;
    private final boolean isStepable;

    CellType(String tileName, boolean isStepable) {
        this.tileName = tileName;
        this.isStepable = isStepable;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isStepable() {
        return isStepable;
    }
}
