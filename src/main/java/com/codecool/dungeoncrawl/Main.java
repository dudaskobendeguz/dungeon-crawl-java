package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.dao.db.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.json.GameJsonManager;
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

import com.codecool.dungeoncrawl.model.SaveSlotModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import java.sql.SQLException;

public class Main extends Application {
    private static String playerName = "Player_1";
    private final static int MAP_SIZE = 15;
    private Level currentLevel = Level.MAIN_MENU;
    private boolean isTimeMageAlive = true;
    GameMap map = MapLoader.getGameMap(currentLevel, new Player(playerName));
    Display display;
    Timer timer;
    GameDatabaseManager dbManager;
    GameJsonManager jsonManager;

    public static void main(String[] args) {
        playerName = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        setupJsonManager();
        display = new Display(MAP_SIZE, primaryStage);
        display.scene.setOnKeyPressed(this::onKeyPressed);
        display.refresh(map);
        startTimer();
        setModalActions();
    }

    private void setModalActions() {
        setSaveModalAction();
        setLoadModalAction();
        setImportModalAction();
        setExportModalAction();
    }

    private void setSaveModalAction() {
        Stage saveModal = display.getSaveModal();
        Button saveButton = display.getSaveButton();
        Button saveCancelButton = display.getSaveCancelButton();
        TextField saveInput = display.getSaveInput();
        Alert overwriteSaveModal = display.getOverwriteSaveModal();

        saveButton.setOnAction((event) -> {
            String name = saveInput.getText();
            SaveSlotModel saveSlot = dbManager.getSaveSlotByName(name);
            if (saveSlot != null) {
                Optional<ButtonType> result = overwriteSaveModal.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    dbManager.updateGame(map, saveSlot, name);
                    closeModal(saveModal);
                }
            } else {
                dbManager.saveGame(map, currentLevel, name);
                closeModal(saveModal);
            }
        });
        saveCancelButton.setOnAction((event) -> closeModal(saveModal));
        saveModal.setOnCloseRequest((windowEvent) -> closeModal(saveModal));
    }

    private void setLoadModalAction() {
        Stage loadModal = display.getLoadModal();
        Button loadButton = display.getLoadButton();
        Button loadCancelButton = display.getLoadCancelButton();
        ListView<SaveSlotModel> loadItems = display.getLoadItems();

        loadModal.setOnCloseRequest((windowEvent) -> closeModal(loadModal));
        loadCancelButton.setOnAction((event -> closeModal(loadModal)));
        loadModal.setOnShown((windowEvent -> {
            loadItems.getItems().clear();
            List<SaveSlotModel> saveSlots = dbManager.getSaveSlots();
            saveSlots.forEach((saveSlot) -> loadItems.getItems().add(saveSlot));
        }));
        loadButton.setOnAction((event -> {
            SaveSlotModel saveSlot = loadItems.getSelectionModel().getSelectedItem();
            if (saveSlot != null) {
                loadGame(saveSlot);
            }
            closeModal(loadModal);
        }));
    }

    private void loadGame(SaveSlotModel saveSlot) {
        map = dbManager.loadGame(saveSlot.getId());
        currentLevel = map.getLevel();
    }

    private void setExportModalAction() {

    }

    private void setImportModalAction() {
        FileChooser importModal = display.getImportModal();
    }

    private void closeModal(Stage modal) {
        modal.close();
        startTimer();
    }

    private void openModal(Stage modal) {
        stopTimer();
        modal.showAndWait();
    }

    private void openFile() {
        FileChooser fileChooser = display.getImportModal();
        File file = fileChooser.showOpenDialog(display.primaryStage);
        if (file != null) {
            if (file.exists()) {
                importGame(file.getAbsolutePath());
            }
        }
    }

    private void saveFile() {
        FileChooser fileChooser = display.getImportModal();
        File file = fileChooser.showSaveDialog(display.primaryStage);
        if (file != null) {
            exportGame(file.getAbsolutePath());
        }
    }

    private void importGame(String filename) {
        map = jsonManager.importGame(filename);
    }

    private void exportGame(String filename) {
        jsonManager.exportGame(map, currentLevel, filename);
    }

    void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runTimer();
            }
        }, 50, 100);
    }

    private void runTimer() {
        Player player = map.getPlayer();
        moveMonsters(false);
        player.tryToAttack(false);
        player.setFireballTimer();
        Platform.runLater(() -> display.refresh(map));
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void setSaveButton() {
        Button saveButton = display.getSaveButton();
        TextField saveInput = display.getSaveInput();
        saveButton.setOnAction((event) -> {
            dbManager.saveGame(map, currentLevel, saveInput.getText());
            display.getSaveModal().close();
            startTimer();
        });
    }

    private void setupJsonManager() {
        jsonManager = new GameJsonManager();
        jsonManager.setup();
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
            case S:
                if (keyEvent.isControlDown()) {
                    openModal(display.getSaveModal());
                }
                break;
            case D:
                if (keyEvent.isControlDown()) {
                    openModal(display.getLoadModal());
                }
                break;
            case F4:
                saveFile();
                break;
            case F5:
                openFile();
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
                } else if (map.getPlayer().getCell().getType().equals(CellType.EXIT)) {
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
        map = MapLoader.getGameMap(currentLevel, map.getPlayer());
        display.refresh(map);
    }

    public void moveMonsters(boolean isTurnBased) {
        List<Monster> monsters = map.getMonsters();
        if (isTimeMageAlive) {
            setIsTimeMageAlive(monsters);
        }
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
