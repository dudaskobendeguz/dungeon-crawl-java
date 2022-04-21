package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;

public enum KeyType {
    SIMPLE_DOOR_KEY("Door key",
            "simple_door_key",
            CellType.SIMPLE_DOOR_CLOSED,
            CellType.SIMPLE_DOOR_OPENED),

    CHEST_KEY("Chest key",
            "chest_key",
            CellType.CHEST_CLOSED,
            CellType.CHEST_OPENED),

    LEVEL_KEY("Level key",
            "level_key",
            CellType.LEVEL_SWITCH_DOOR_CLOSED,
            CellType.LEVEL_SWITCH_DOOR_OPENED);

    private final String name;
    private final String tileName;
    private final CellType closedDoor;
    private final CellType openedDoor;

    KeyType(String name, String tileName, CellType closedDoor, CellType openedDoor) {
        this.name = name;
        this.tileName = tileName;
        this.closedDoor = closedDoor;
        this.openedDoor = openedDoor;
    }

    public String getTileName() {
        return tileName;
    }

    public CellType getClosedDoorType() {
        return closedDoor;
    }

    public CellType getOpenedDoor() {
        return openedDoor;
    }

    @Override
    public String toString() {
        return name;
    }
}
