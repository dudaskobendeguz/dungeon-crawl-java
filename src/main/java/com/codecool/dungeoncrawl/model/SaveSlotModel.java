package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.Level;

import java.sql.Date;
import java.util.Arrays;

public class SaveSlotModel extends BaseModel {
    private Date savedAt;
    private int levelId;
    private String name;

    public SaveSlotModel(int levelId) {
        this.levelId = levelId;
    }

    public SaveSlotModel(int levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

    private Integer generateId() {
        return Math.abs(java.time.LocalTime.now().hashCode());
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

    public String getMapFilePath() {
        return Arrays.stream(Level.values())
                .filter(level -> level.getID() == levelId)
                .findFirst()
                .orElseThrow().getMAP_FILE_PATH();
    }

    public String getName() {
        return name;
    }
}
