package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.KeyType;
import javafx.application.Application;
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

import java.util.List;

public class Main extends Application {
    private final static int MAP_SIZE = 15;
    private Levels currentLevel = Levels.LEVEL_1;
    GameMap map = MapLoader.loadMap(currentLevel.getMapFilePath(), new Player());
    Canvas canvas = new Canvas(
            MAP_SIZE * Tiles.TILE_WIDTH,
            MAP_SIZE * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    GridPane ui = new GridPane();
    Label healthLabel = new Label();
    Label damageLabel = new Label();
    Label itemsLabel = new Label();
    Label weaponLabel = new Label();
    private int uiRowIndex = 0;

    private enum Levels {
        TEST_LEVEL("/custom_map/test_level.csv"),
        LEVEL_1("/custom_map/level_1.csv"),
        LEVEL_2("/custom_map/level_2.csv");

        private final String mapFilePath;

        Levels(String mapFilePath) {
            this.mapFilePath = mapFilePath;
        }

        public String getMapFilePath() {
            return mapFilePath;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, uiRowIndex);
        addUiLabel(healthLabel, 1);
        ui.add(new Label("Damage: "), 0, uiRowIndex);
        addUiLabel(damageLabel, 1);
        ui.add(new Label("Weapon: "), 0, uiRowIndex);
        addUiLabel(weaponLabel, 1);

        addUiLabel(new Label("Inventory"), 0);
        addUiLabel(itemsLabel, 0);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                moveActors(0, -1);
                refresh();
                break;
            case DOWN:
                moveActors(0, 1);
                refresh();
                break;
            case LEFT:
                moveActors(-1, 0);
                refresh();
                break;
            case RIGHT:
                moveActors(1, 0);
                refresh();
                break;
            case E:
                map.getPlayer().tryToUseKey();
                refresh();
                break;
        }
        if (map.getPlayer().isAboutToDie()) {
            System.exit(0);
        }
    }


    public void moveActors(int dx, int dy) {
        map.getPlayer().move(dx, dy);
        if (map.getPlayer().isTryingToSwitchLevel()) {
            switchLevel();
        }
        map.getPlayer().tryToPickUpItem();
        moveMonsters();
    }

    private void switchLevel() {
        switch (currentLevel) {
            case LEVEL_1: {
                currentLevel = Levels.LEVEL_2;
                break;
            }
        }
        map = MapLoader.loadMap(currentLevel.getMapFilePath(), map.getPlayer());
        refresh();
    }


    public void moveMonsters() {
        List<Monster> monsters = map.getMonsters();
        Cell playerCell = map.getPlayer().getCell();
        int playerX = playerCell.getX();
        int playerY = playerCell.getY();
        monsters.removeIf(monster -> monster.getCell() == null);
        monsters.forEach(monster -> monster.move(playerX, playerY));
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, MAP_SIZE, MAP_SIZE);
        Cell playerCell = map.getPlayer().getCell();
        int playerX = playerCell.getX();
        int playerY = playerCell.getY();
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                Cell cell = map.getCell(playerX - (MAP_SIZE / 2) + x, playerY - (MAP_SIZE / 2) + y);
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
        healthLabel.setText("" + map.getPlayer().getHealth());
        damageLabel.setText("" + map.getPlayer().getWeaponDamage());
        weaponLabel.setText(map.getPlayer().getWeapon().toString());
        drawItems();
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
            if (numberOfKeys > 0) {
                keysString.append(String.format("%s : %s%n", keyType.toString(), numberOfKeys));
            }
        }
        return keysString.toString();
    }

    private String getConsumablesString() {
        StringBuilder consumables = new StringBuilder("Foods: \n");
        for (ConsumableType consumableType : ConsumableType.values()) {
            int numberOfConsumable = map.getPlayer().countConsumables(consumableType);
            if (numberOfConsumable > 0) {
                consumables.append(String.format("%s : %s%n", consumableType.toString(), numberOfConsumable));
            }
        }
        return consumables.toString();
    }


    private void addUiLabel(Label label, int colIndex) {
        ui.add(label, colIndex, uiRowIndex++);
    }
}
