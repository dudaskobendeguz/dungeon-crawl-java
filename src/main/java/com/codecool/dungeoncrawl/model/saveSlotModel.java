package com.codecool.dungeoncrawl.model;

import java.sql.Date;

public class saveSlotModel extends BaseModel {
    private Date savedAt;
    private int levelId;

    public saveSlotModel(int levelId) {
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
