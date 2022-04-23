package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY(0),
    FLOOR_1(4, true),
    FLOOR_2(1, true),
    TIME_MAGE_FLOOR(588),
    BRICK_FLOOR(16, true),
    TREE_1(32),
    TREE_2(33),
    TREE_3(34),
    TREE_4(35),
    TREE_5(36),
    TREE_6(37),
    TREE_7(64, true),
    TREE_8(65, true),
    TREE_9(66, true),
    TREE_10(67),
    TREE_11(68),
    TREE_12(69, true),
    TREE_13(5, true),
    TREE_14(6, true),
    TREE_15(7, true),
    TREE_16(179),
    TREE_17(180),
    TREE_18(205, true),
    TREE_19(206, true),
    TREE_20(207, true),
    TREE_21(208, true),
    TREE_22(209, true),
    TREE_23(210),
    TREE_24(211),
    TREE_25(212),
    WALL(586),

    GRAVE_1(449, true),
    GRAVE_2(448, true),
    BONES(480, true),
    CANDLES(485),
    HOUSE_1(644),
    LEVEL_SWITCH_HOUSE(647, true, false, true),
    LEVEL_SWITCH_STAIRS(194, true, false, true),
    HOUSE_2(673),
    SIMPLE_DOOR_OPENED(294, true, false),
    SIMPLE_DOOR_CLOSED(291, false, true),
    LEVEL_SWITCH_DOOR_OPENED(289, true, false, true),
    LEVEL_SWITCH_DOOR_CLOSED(288, false, true),
    CHEST_OPENED(201, true),
    CHEST_CLOSED(200, false, true);

    private final int ID;
    private final boolean isLevelSwitcher;
    private final boolean isStepable;
    private final boolean isOpenable;

    CellType(int tileId, boolean isStepable, boolean isOpenable, boolean isLevelSwitcher) {
        this.ID = tileId;
        this.isLevelSwitcher = isLevelSwitcher;
        this.isStepable = isStepable;
        this.isOpenable = isOpenable;
    }

    CellType(int tileId, boolean isStepable, boolean isOpenable) {
        this(tileId, isStepable, isOpenable, false);
    }

    CellType(int tileId) {
        this(tileId, false, false, false);
    }

    CellType(int tileId, boolean isStepable) {
        this(tileId, isStepable, false, false);
    }

    public int getTileId() {
        return ID;
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
