package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actor.monsters.Fireball;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.monsters.Movable;
import com.codecool.dungeoncrawl.logic.actor.monsters.TimeMage;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import java.sql.SQLException;

public class Main extends Application {
    private static String playerName = "Player_1";
    private final static int MAP_SIZE = 15;
    private Levels currentLevel = Levels.MAIN_MENU;
    private boolean isTimeMageAlive = true;
    GameMap map = MapLoader.getGameMap(currentLevel.getMapFilePath(), new Player(playerName));
    Display display;
    Timer timer = new Timer();
    GameDatabaseManager dbManager;

    private enum Levels {
        MAIN_MENU("/custom_map/main_menu.csv"),
        TEST_LEVEL("/custom_map/test_level.csv"),
        LEVEL_1("/custom_map/level_1.csv"),
        LEVEL_2("/custom_map/level_2.csv"),
        LEVEL_3("/custom_map/level_3.csv"),
        LEVEL_4("/custom_map/level_4.csv"),
        LEVEL_5("/custom_map/level_5.csv"),
        LEVEL_6("/custom_map/level_6.csv"),
        GRAVEYARD("/custom_map/graveyard.csv");

        private final String mapFilePath;

        Levels(String mapFilePath) {
            this.mapFilePath = mapFilePath;
        }

        public String getMapFilePath() {
            return mapFilePath;
        }
    }

    public static void main(String[] args) {
        playerName = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        display = new Display(MAP_SIZE, primaryStage);
        display.scene.setOnKeyPressed(this::onKeyPressed);
        display.refresh(map);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Player player = map.getPlayer();
                moveMonsters(false);
                player.tryToAttack(false);
                player.setFireballTimer();
                Platform.runLater(() -> display.refresh(map));
            }
        }, 1000, 100);
    }

    void onKeyPressed(KeyEvent keyEvent) {
        Player player = map.getPlayer();
        switch (keyEvent.getCode()) {
            case UP:
                moveActors(Direction.UP);
                display.refresh(map);
                break;
            case DOWN:
                moveActors(Direction.DOWN);
                display.refresh(map);
                break;
            case LEFT:
                moveActors(Direction.LEFT);
                display.refresh(map);
                break;
            case RIGHT:
                moveActors(Direction.RIGHT);
                display.refresh(map);
                break;
            case E:
                player.tryToUseKey();
                display.refresh(map);
                break;
            case DIGIT1:
                player.tryToUseInventory(ConsumableType.APPLE);
                display.refresh(map);
                break;
            case DIGIT2:
                player.tryToUseInventory(ConsumableType.BREAD);
                display.refresh(map);
                break;
            case DIGIT3:
                player.tryToUseInventory(ConsumableType.MEAT);
                display.refresh(map);
                break;
            case SPACE:
                if (player.isMage() && player.isAbleToShootFireball()) {
                    Fireball fireball = player.getFireball();
                    if (fireball != null) {
                        map.addMonster(fireball);
                        display.refresh(map);
                    }
                }
                break;
            case ESCAPE:
                System.exit(0);
                break;
            case S: // new line
                dbManager.savePlayer(player);
                break;

        }
    }

    public void moveActors(Direction direction) {
        Player player = map.getPlayer();
        player.move(direction);
        if (player.isTryingToSwitchLevel()) {
            switchLevel();
        }
        player.tryToPickUpItem();
        moveMonsters(true);
        player.tryToAttack(true);
        if (player.isAboutToDie()) {
            currentLevel = Levels.GRAVEYARD;
            switchLevel();
        }
    }

    private void switchLevel() {
        switch (currentLevel) {
            case MAIN_MENU:
                if (map.getPlayer().getCell().getType().equals(CellType.HOME)) {
                    currentLevel = Levels.LEVEL_1;
                } else if (map.getPlayer().getCell().getType().equals(CellType.FLOPPY)) {
                    // TODO Implement load level
                    System.out.println("Implement load level");
                }else if (map.getPlayer().getCell().getType().equals(CellType.EXIT)) {
                    System.exit(0);
                }
                break;
            case LEVEL_1: {
                currentLevel = Levels.LEVEL_2;
                break;
            }
            case LEVEL_2: {
                currentLevel = Levels.LEVEL_3;
                break;
            }
            case LEVEL_3: {
                currentLevel = Levels.LEVEL_4;
                break;
            }
            case LEVEL_4: {
                currentLevel = Levels.LEVEL_5;
                break;
            }
            case LEVEL_5: {
                currentLevel = Levels.LEVEL_6;
                break;
            }
            case GRAVEYARD: {
                break;
            }
        }
        map = MapLoader.getGameMap(currentLevel.getMapFilePath(), map.getPlayer());
        display.refresh(map);
    }

    public void moveMonsters(boolean isTurnBased) {
        List<Monster> monsters = map.getMonsters();
        if (isTimeMageAlive) {setIsTimeMageAlive(monsters);}
        clearDeadMonsters(monsters);

        Monster teleportedMonster = null;
        for (Monster monster : monsters) {
            if (monster instanceof Movable) {
                if (isTurnBased && monster.isTurnBased() || !isTurnBased && !monster.isTurnBased()) {
                    ((Movable) monster).move(map.getPlayerX(), map.getPlayerY(), isTimeMageAlive);
                    if (monster instanceof TimeMage) {
                        teleportedMonster = ((TimeMage) monster).attack();
                    }
                }
            }
        }
        if (teleportedMonster != null) {
            map.addMonster(teleportedMonster);
        }
    }

    private void setIsTimeMageAlive(List<Monster> monsters) {
        for (Monster monster : monsters) {
            if (monster.getCell() == null && monster instanceof TimeMage) {
                isTimeMageAlive = false;
                break;
            }
        }
    }

    private void clearDeadMonsters(List<Monster> monsters) {
        monsters.removeIf(monster -> monster.getCell() == null);
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }
}
