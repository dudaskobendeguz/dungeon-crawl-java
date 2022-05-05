package com.codecool.dungeoncrawl.logic.actor.monsters;

import com.codecool.dungeoncrawl.TileCategory;
import com.codecool.dungeoncrawl.TileType;
import com.codecool.dungeoncrawl.logic.Cell;

public enum MonsterType implements TileType {
    SKELETON(93),
    CHICKEN(250),
    SLIME(283),
    ROBOT(122),
    TIME_MAGE(88),
    FIREBALL(335);

    private final int ID;

    MonsterType(int tileId) {
        this.ID = tileId;
    }

    public int getTileId() {
        return ID;
    }

    @Override
    public TileCategory getTileCategory() {
        return TileCategory.MONSTER;
    }

    public static Monster getMonsterByMonsterType(MonsterType monsterType, Cell cell) {
        switch (monsterType) {
            case SKELETON:
                return new Skeleton(cell);
            case SLIME:
                return new Slime(cell);
            case CHICKEN:
                return new Chicken(cell);
            case ROBOT:
                return new Robot(cell);
            case TIME_MAGE:
                return new TimeMage(cell);
            case FIREBALL:
                return new Fireball(cell);
            default:
                throw new RuntimeException("Unrecognized MonsterType: " + monsterType);
        }
    }
}
