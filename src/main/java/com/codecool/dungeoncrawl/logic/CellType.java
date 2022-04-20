package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    TREE("tree"),
    WALL("wall"),
    DOOR_OPENED("door_opened"),
    DOOR_CLOSED("door_closed");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
