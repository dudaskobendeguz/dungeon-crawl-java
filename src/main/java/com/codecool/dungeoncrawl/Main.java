package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actor.monsters.Fireball;
import com.codecool.dungeoncrawl.logic.actor.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actor.monsters.Movable;
import com.codecool.dungeoncrawl.logic.actor.monsters.TimeMage;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.KeyType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import java.sql.SQLException;

public class Main extends Application {
    private static String playerName = "Player_1";
    private final static int MAP_SIZE = 15;
    private Levels currentLevel = Levels.LEVEL_1;
    private boolean isTimeMageAlive = true;
    GameMap map = MapLoader.loadMap(currentLevel.getMapFilePath(), new Player(playerName));
    Canvas canvas = new Canvas(
            MAP_SIZE * Tiles.TILE_WIDTH,
            MAP_SIZE * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    GridPane ui = new GridPane();
    Label playerNameLabel = new Label();
    Label healthLabel = new Label();
    Label damageLabel = new Label();
    Label itemsLabel = new Label();
    Label weaponLabel = new Label();
    private int uiRowIndex = 0;
    Timer timer = new Timer();
    GameDatabaseManager dbManager;

    private enum Levels {
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
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Player Name : "), 0, uiRowIndex);
        addUiLabel(playerNameLabel, 1);

        ui.add(new Label("Health: "), 0, uiRowIndex);
        addUiLabel(healthLabel, 1);
        ui.add(new Label("Damage: "), 0, uiRowIndex);
        addUiLabel(damageLabel, 1);
        ui.add(new Label("Weapon: "), 0, uiRowIndex);
        addUiLabel(weaponLabel, 1);
        addUiLabel(new Label("---------"), 0);
        addUiLabel(new Label("Inventory"), 0);
        addUiLabel(new Label(""), 0);
        addUiLabel(itemsLabel, 0);
        addUiLabel(new Label(""), 0);
        addUiLabel(new Label(""), 0);
        addUiLabel(new Label(""), 0);
        addUiLabel(new Label(""), 0);

        addUiLabel(new Label("KEY BINDINGS"), 0);
        addUiLabel(new Label("Up: UP"), 0);
        addUiLabel(new Label("Down: DOWN"), 0);
        addUiLabel(new Label("Left: LEFT"), 0);
        addUiLabel(new Label("Right: RIGHT"), 0);
        addUiLabel(new Label("Shoot: SPACE"), 0);
        addUiLabel(new Label("Interact: E"), 0);
        addUiLabel(new Label("Eat apple: 1"), 0);
        addUiLabel(new Label("Eat bread: 2"), 0);
        addUiLabel(new Label("Eat meat: 3"), 0);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Player player = map.getPlayer();
                moveMonsters(false);
                player.tryToAttack(false);
                player.setFireballTimer();
                Platform.runLater(() -> refresh());
            }
        }, 1000, 100);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Player player = map.getPlayer();
        switch (keyEvent.getCode()) {
            case UP:
                moveActors(Direction.UP);
                refresh();
                break;
            case DOWN:
                moveActors(Direction.DOWN);
                refresh();
                break;
            case LEFT:
                moveActors(Direction.LEFT);
                refresh();
                break;
            case RIGHT:
                moveActors(Direction.RIGHT);
                refresh();
                break;
            case E:
                player.tryToUseKey();
                refresh();
                break;
            case DIGIT1:
                player.tryToUseInventory(ConsumableType.APPLE);
                refresh();
                break;
            case DIGIT2:
                player.tryToUseInventory(ConsumableType.BREAD);
                refresh();
                break;
            case DIGIT3:
                player.tryToUseInventory(ConsumableType.MEAT);
                refresh();
                break;
            case SPACE:
                if (player.isMage() && player.isAbleToShootFireball()) {
                    Fireball fireball = player.getFireball();
                    if (fireball != null) {
                        map.addMonster(fireball);
                        refresh();
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
        map = MapLoader.loadMap(currentLevel.getMapFilePath(), map.getPlayer());
        refresh();
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
                    ((Movable) monster).move(map.getPlayer().getX(), map.getPlayer().getY(), isTimeMageAlive);
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
            if (monster.getCell() == null) {
                if (monster instanceof TimeMage) {
                    isTimeMageAlive = false;
                    break;
                }
            }
        }
    }

    private void clearDeadMonsters(List<Monster> monsters) {
        monsters.removeIf(monster -> monster.getCell() == null);
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, MAP_SIZE, MAP_SIZE);
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                Cell cell = map.getCell(map.getPlayer().getX() - (MAP_SIZE / 2) + x, map.getPlayer().getY() - (MAP_SIZE / 2) + y);
                if (cell == null) {
                    Tiles.drawTile(context, new Cell(), x, y);
                } else if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        playerNameLabel.setText(map.getPlayer().getName());
        healthLabel.setText(String.format("%s %s", map.getPlayer().getHealth(), displayHealthBar()));
        damageLabel.setText("" + map.getPlayer().getDamage());
        weaponLabel.setText(map.getPlayer().getWeaponName());
        drawItems();
    }

    private String displayHealthBar() {
        double currentHealth = map.getPlayer().getHealth();
        double maxHealth = map.getPlayer().getMaxHealth();
        double hp = (currentHealth / maxHealth) * 10;
        return "♥".repeat(Math.max(0, (int) hp));
    }

    private void drawItems() {
        getConsumablesString();
        getKeysString();
        itemsLabel.setText(String.format("%s%n%s", getConsumablesString(), getKeysString()));
    }

    private String getKeysString() {
        StringBuilder keysString = new StringBuilder("Keys: \n");
        for (KeyType keyType : KeyType.values()) {
            int numberOfKeys = map.getPlayer().countKeys(keyType);
            keysString.append(String.format("%s :   %s%n", keyType.getName(), numberOfKeys));
        }
        return keysString.toString();
    }

    private String getConsumablesString() {
        StringBuilder consumables = new StringBuilder("Foods: \n");
        for (ConsumableType consumableType : ConsumableType.values()) {
            int numberOfConsumable = map.getPlayer().countConsumables(consumableType);
            consumables.append(String.format("%s :  %s%n", consumableType.getName(), numberOfConsumable));
        }
        return consumables.toString();
    }


    private void addUiLabel(Label label, int colIndex) {
        ui.add(label, colIndex, uiRowIndex++);
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
