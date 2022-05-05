package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.Level;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class SaveSlotModel extends BaseModel {
    private String name;
    private Timestamp savedAt;
    private Level level;

    public SaveSlotModel(int levelId) {
        setLevelById(levelId);
    }

    public SaveSlotModel(int levelId, String name) {
        setLevelById(levelId);
        this.name = name;
    }

    private void setLevelById(int levelId) {
        this.level = Arrays.stream(Level.values())
                .filter(level -> level.getID() == levelId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }

    public int getLevelId() {
        return level.getID();
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

    public Level getLevel() {
        return this.level;
    }
}
