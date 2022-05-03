package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
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

import java.sql.SQLException;

public class Main extends Application {
    private static String playerName = "Player_1";
    private final static int MAP_SIZE = 15;
    private Level currentLevel = Level.MAIN_MENU;
    private boolean isTimeMageAlive = true;
    GameMap map = MapLoader.getGameMap(currentLevel.getMAP_FILE_PATH(), new Player(playerName));
    Display display;
    Timer timer = new Timer();
    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        playerName = args[0];
        launch(args);
    }

    private void loadSave(int id) {
        // TODO Implement loadSave
        // get Everything according to that id from database
        // -> GameDatabaseManager methods
        // Load Level
        // Load Player
        // Load Monsters
        // Load Items

        // Set Level
        // Send Models to MapLoader
        // Set Map
        // Refresh
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

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
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

                dbManager.saveGame(map, currentLevel);
                break;
            case D:
//                map = dbManager.loadGame(2);
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
            currentLevel = Level.GRAVEYARD;
            switchLevel();
        }
    }

    private void switchLevel() {
        switch (currentLevel) {
            case MAIN_MENU:
                if (map.getPlayer().getCell().getType().equals(CellType.HOME)) {
                    currentLevel = Level.LEVEL_1;
                } else if (map.getPlayer().getCell().getType().equals(CellType.FLOPPY)) {
                    // TODO Implement load level
                    System.out.println("Implement load level");
                }else if (map.getPlayer().getCell().getType().equals(CellType.EXIT)) {
                    System.exit(0);
                }
                break;
            case LEVEL_1: {
                currentLevel = Level.LEVEL_2;
                break;
            }
            case LEVEL_2: {
                currentLevel = Level.LEVEL_3;
                break;
            }
            case LEVEL_3: {
                currentLevel = Level.LEVEL_4;
                break;
            }
            case LEVEL_4: {
                currentLevel = Level.LEVEL_5;
                break;
            }
            case LEVEL_5: {
                currentLevel = Level.LEVEL_6;
                break;
            }
            case GRAVEYARD: {
                break;
            }
        }
        map = MapLoader.getGameMap(currentLevel.getMAP_FILE_PATH(), map.getPlayer());
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
}
