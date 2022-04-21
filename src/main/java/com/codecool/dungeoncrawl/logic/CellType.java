package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", false, false),
    FLOOR("floor", true, false),
    TREE("tree", false, false),
    WALL("wall", false, false),
    SIMPLE_DOOR_OPENED("simple_door_opened", true, false),
    SIMPLE_DOOR_CLOSED("simple_door_closed", false, true),
    LEVEL_DOOR_OPENED("level_door_opened", true, false),
    LEVEL_DOOR_CLOSED("level_door_closed", false, true),
    CHEST_OPENED("chest_opened", true, false),
    CHEST_CLOSED("chest_closed", false, true);

    private final String tileName;
    private final boolean isStepable;
    private final boolean isOpenable;

    CellType(String tileName, boolean isStepable, boolean isOpenable) {
        this.tileName = tileName;
        this.isStepable = isStepable;
        this.isOpenable = isOpenable;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isStepable() {
        return isStepable;
    }

    public boolean isOpenable() {
        return isOpenable;
    }
}
