package com.codecool.dungeoncrawl.logic;

import java.util.Arrays;

public enum Direction {
    UP(0, -1, 1),
    DOWN(0, 1, 2),
    LEFT(-1, 0, 3),
    RIGHT(1, 0, 4);

    private final int x;
    private final int y;
    private final int ID;

    Direction(int x, int y, int id) {
        this.x = x;
        this.y = y;
        ID = id;
    }

    public static Direction getDirectionById(int id) {
        for (Direction direction : values()) {
            if (direction.ID == id) {
                return direction;
            }
        }
        throw new RuntimeException("There is no Direction with id: " + id);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getID() {
        return ID;
    }
}
