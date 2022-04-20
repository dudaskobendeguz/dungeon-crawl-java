package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    TREE("tree"),
    WALL("wall"),
    SIMPLE_DOOR_OPENED("simple_door_opened"),
    SIMPLE_DOOR_CLOSED("simple_door_closed");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
