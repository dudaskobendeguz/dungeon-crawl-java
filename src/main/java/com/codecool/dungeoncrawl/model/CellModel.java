package com.codecool.dungeoncrawl.model;

public class CellModel extends BaseModel {
    private int typeId;
    private int x;
    private int y;


    public CellModel(int typeId, int x, int y) {
        this.typeId = typeId;
        this.x = x;
        this.y = y;
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
}
