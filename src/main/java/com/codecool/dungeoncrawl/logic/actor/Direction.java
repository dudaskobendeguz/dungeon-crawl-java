package com.codecool.dungeoncrawl.logic.actor;

public enum Direction {
    EAST(1, 0),
    NORTH(0, -1),
    SOUTH(1, 0),
    WEST(-1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
