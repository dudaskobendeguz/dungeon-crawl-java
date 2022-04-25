package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.TileType;
import com.codecool.dungeoncrawl.LoadableTile;

public enum CellType implements LoadableTile {
    EMPTY(0),
    FLOOR_1(4, CellAttribute.STEPABLE),
    FLOOR_2(1, CellAttribute.STEPABLE),
    TIME_MAGE_FLOOR(588),
    BRICK_FLOOR(16, CellAttribute.STEPABLE),
    TREE_1(32),
    TREE_2(33),
    TREE_3(34),
    TREE_4(35),
    TREE_5(36),
    TREE_6(37),
    TREE_7(64, CellAttribute.STEPABLE),
    TREE_8(65, CellAttribute.STEPABLE),
    TREE_9(66, CellAttribute.STEPABLE),
    TREE_10(67),
    TREE_11(68),
    TREE_12(69, CellAttribute.STEPABLE),
    TREE_13(5, CellAttribute.STEPABLE),
    TREE_14(6, CellAttribute.STEPABLE),
    TREE_15(7, CellAttribute.STEPABLE),
    TREE_16(179),
    TREE_17(180),
    TREE_18(205, CellAttribute.STEPABLE),
    TREE_19(206, CellAttribute.STEPABLE),
    TREE_20(207, CellAttribute.STEPABLE),
    TREE_21(208, CellAttribute.STEPABLE),
    TREE_22(209, CellAttribute.STEPABLE),
    TREE_23(210),
    TREE_24(211),
    TREE_25(212),
    WALL(586),

    GRAVE_1(449, CellAttribute.STEPABLE),
    GRAVE_2(448, CellAttribute.STEPABLE),
    BONES(480, CellAttribute.STEPABLE),
    CANDLES(485),
    HOUSE_1(644),
    LEVEL_SWITCH_HOUSE(647, CellAttribute.LEVEL_SWITCHER),
    LEVEL_SWITCH_STAIRS(194, CellAttribute.LEVEL_SWITCHER),
    HOUSE_2(673),
    SIMPLE_DOOR_OPENED(294, CellAttribute.STEPABLE),
    SIMPLE_DOOR_CLOSED(291, CellAttribute.OPENABLE),
    LEVEL_SWITCH_DOOR_OPENED(289, CellAttribute.LEVEL_SWITCHER),
    LEVEL_SWITCH_DOOR_CLOSED(288, CellAttribute.OPENABLE),
    CHEST_OPENED(201, CellAttribute.STEPABLE),
    CHEST_CLOSED(200, CellAttribute.OPENABLE);

    private enum CellAttribute {
        DECORATIVE(false, false, false),
        STEPABLE(true, false, false),
        OPENABLE(false, true, false),
        LEVEL_SWITCHER(true, false, true);

        private final boolean isStepable;
        private final boolean isOpenable;
        private final boolean isLevelSwitcher;

        CellAttribute(boolean isStepable, boolean isOpenable, boolean isLevelSwitcher) {
            this.isStepable = isStepable;
            this.isOpenable = isOpenable;
            this.isLevelSwitcher = isLevelSwitcher;
        }
    }

    private final int tileId;
    private final CellAttribute cellAttribute;

    CellType(int tileId) {
        this(tileId, CellAttribute.DECORATIVE);
    }

    CellType(int tileId, CellAttribute cellAttribute) {
        this.tileId = tileId;
        this.cellAttribute = cellAttribute;
    }

    public int getTileId() {
        return tileId;
    }

    @Override
    public TileType getTileType() {
        return TileType.CELL;
    }

    public boolean isStepable() {
        return cellAttribute.isStepable;
    }

    public boolean isLevelSwitcher() {
        return cellAttribute.isLevelSwitcher;
    }

    public boolean isOpenable() {
        return cellAttribute.isOpenable;
    }
}
