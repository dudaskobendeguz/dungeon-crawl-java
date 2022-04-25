package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.TileCategory;
import com.codecool.dungeoncrawl.TileType;

public enum CellType implements TileType {
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
    CHEST_CLOSED(200, CellAttribute.OPENABLE),

    //ICONS
    HOME(921, CellAttribute.LEVEL_SWITCHER),
    FLOPPY(922, CellAttribute.LEVEL_SWITCHER),
    EXIT(824, CellAttribute.LEVEL_SWITCHER),

    // Alphabet
    NUMERIC_0(947),
    NUMERIC_1(948),
    NUMERIC_2(949),
    NUMERIC_3(950),
    NUMERIC_4(951),
    NUMERIC_5(952),
    NUMERIC_6(953),
    NUMERIC_7(954),
    NUMERIC_8(955),
    NUMERIC_9(956),
    COLON(957),
    DOT(958),
    PERCENT(959),
    A(979),
    B(980),
    C(981),
    D(982),
    E(983),
    F(984),
    G(985),
    H(986),
    I(987),
    J(988),
    K(989),
    L(990),
    M(991),
    N(1011),
    O(1012),
    P(1013),
    Q(1014),
    R(1015),
    S(1016),
    T(1017),
    U(1018),
    V(1019),
    W(1020),
    X(1021),
    Y(1022),
    Z(1023);

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
    public TileCategory getTileCategory() {
        return TileCategory.CELL;
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
