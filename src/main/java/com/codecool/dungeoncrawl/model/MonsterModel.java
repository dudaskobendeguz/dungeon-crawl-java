package com.codecool.dungeoncrawl.model;

public class MonsterModel extends BaseModel {
    private int typeId;
    private int x;
    private int y;
    private int hp;
    private int directionId;
    private int moveTimer;

    public MonsterModel(int typeId, int x, int y, int hp) {
        this.typeId = typeId;
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDirectionId() {
        return directionId;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public int getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(int moveTimer) {
        this.moveTimer = moveTimer;
    }
}
