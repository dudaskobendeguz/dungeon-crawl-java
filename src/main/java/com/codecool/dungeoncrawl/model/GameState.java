package com.codecool.dungeoncrawl.model;

import java.sql.Date;

public class GameState extends BaseModel {
    private Date savedAt;
    private int levelId;

    public GameState(int levelId) {
        this.levelId = levelId;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public int getLevelId() {
        return levelId;
    }
}
