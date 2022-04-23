package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;

public enum KeyType {
    SIMPLE_DOOR_KEY(
            754,
            "Door key",
            CellType.SIMPLE_DOOR_CLOSED,
            CellType.SIMPLE_DOOR_OPENED
    ),

    CHEST_KEY(
            752,
            "Chest key",
            CellType.CHEST_CLOSED,
            CellType.CHEST_OPENED
    ),

    LEVEL_KEY(
            753,
            "Level key",
            CellType.LEVEL_SWITCH_DOOR_CLOSED,
            CellType.LEVEL_SWITCH_DOOR_OPENED
    );

    private final int ID;
    private final String name;
    private final CellType closedDoor;
    private final CellType openedDoor;

    KeyType(int tileId, String name, CellType closedDoor, CellType openedDoor) {
        this.ID = tileId;
        this.name = name;
        this.closedDoor = closedDoor;
        this.openedDoor = openedDoor;
    }

    public int getTileId() {
        return ID;
    }

    public CellType getClosedDoorType() {
        return closedDoor;
    }

    public CellType getOpenedDoor() {
        return openedDoor;
    }

    public String getName() {
        return name;
    }
}
