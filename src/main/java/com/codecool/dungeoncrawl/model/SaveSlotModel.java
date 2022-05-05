package com.codecool.dungeoncrawl.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.codecool.dungeoncrawl.Level;

import java.sql.Date;
import java.util.Arrays;

public class SaveSlotModel extends BaseModel {
    private String name;
    private Timestamp savedAt;
    private int levelId;

    public SaveSlotModel(int levelId) {
        this.levelId = levelId;
    }

    public SaveSlotModel(int levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%d: %s - %s", id, name, new SimpleDateFormat("yyyy/MM/dd - hh:mm:ss").format(savedAt));
    }
}
