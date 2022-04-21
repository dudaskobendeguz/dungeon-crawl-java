package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR_1("floor_1", true),
    FLOOR_2("floor_2", true),
    TREE_1("tree_1"),
    TREE_2("tree_2"),
    TREE_3("tree_3"),
    TREE_4("tree_4"),
    TREE_5("tree_5"),
    TREE_6("tree_6"),
    TREE_7("tree_7", true),
    TREE_8("tree_8", true),
    TREE_9("tree_9", true),
    TREE_10("tree_10"),
    TREE_11("tree_11"),
    TREE_12("tree_12", true),
    TREE_13("tree_13", true),
    TREE_14("tree_14", true),
    TREE_15("tree_15", true),
    TREE_16("tree_16"),
    TREE_17("tree_17"),
    TREE_18("tree_18", true),
    TREE_19("tree_19", true),
    TREE_20("tree_20", true),
    TREE_21("tree_21", true),
    TREE_22("tree_22", true),
    TREE_23("tree_23"),
    TREE_24("tree_24"),
    TREE_25("tree_25"),
    WALL("wall"),

    GRAVE_1("grave_1", true),
    GRAVE_2("grave_2", true),
    BONES("bones", true),
    CANDLES("candles"),
    HOUSE_1("house_1"),
    LEVEL_SWITCH_HOUSE("level_switch_house", true, false, true),
    HOUSE_2("house_2"),
    SIMPLE_DOOR_OPENED("simple_door_opened", true, false),
    SIMPLE_DOOR_CLOSED("simple_door_closed", false, true),
    LEVEL_SWITCH_DOOR_OPENED("level_switch_door_opened", true, false, true),
    LEVEL_SWITCH_DOOR_CLOSED("level_switch_door_closed", false, true),
    CHEST_OPENED("chest_opened", true),
    CHEST_CLOSED("chest_closed", false, true);

    private final boolean isLevelSwitcher;
    private final String tileName;
    private final boolean isStepable;
    private final boolean isOpenable;

    CellType(String tileName, boolean isStepable, boolean isOpenable, boolean isLevelSwitcher) {
        this.isLevelSwitcher = isLevelSwitcher;
        this.tileName = tileName;
        this.isStepable = isStepable;
        this.isOpenable = isOpenable;
    }

    CellType(String tileName, boolean isStepable, boolean isOpenable) {
        this.isLevelSwitcher = false;
        this.tileName = tileName;
        this.isStepable = isStepable;
        this.isOpenable = isOpenable;
    }

    CellType(String tileName) {
        this.isLevelSwitcher = false;
        this.tileName = tileName;
        this.isStepable = false;
        this.isOpenable = false;
    }

    CellType(String tileName, boolean isStepable) {
        this.isLevelSwitcher = false;
        this.tileName = tileName;
        this.isStepable = isStepable;
        this.isOpenable = false;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isStepable() {
        return isStepable;
    }

    public boolean isLevelSwitcher() {
        return isLevelSwitcher;
    }

    public boolean isOpenable() {
        return isOpenable;
    }
}
