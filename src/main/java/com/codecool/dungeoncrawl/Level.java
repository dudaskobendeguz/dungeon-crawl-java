package com.codecool.dungeoncrawl;

public enum Level {
    MAIN_MENU("/custom_map/main_menu.csv", 0),
    TEST_LEVEL("/custom_map/test_level.csv", -2),
    LEVEL_1("/custom_map/level_1.csv", 1),
    LEVEL_2("/custom_map/level_2.csv", 2),
    LEVEL_3("/custom_map/level_3.csv", 3),
    LEVEL_4("/custom_map/level_4.csv", 4),
    LEVEL_5("/custom_map/level_5.csv", 5),
    LEVEL_6("/custom_map/level_6.csv", 6),
    GRAVEYARD("/custom_map/graveyard.csv", -1);

    private final String MAP_FILE_PATH;
    private final int ID;

    Level(String MAP_FILE_PATH, int id) {
        this.MAP_FILE_PATH = MAP_FILE_PATH;
        ID = id;
    }

    public String getMAP_FILE_PATH() {
        return MAP_FILE_PATH;
    }

    public int getID() {
        return ID;
    }
}
