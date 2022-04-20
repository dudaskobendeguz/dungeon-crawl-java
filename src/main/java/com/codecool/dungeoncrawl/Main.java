package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.KeyType;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    GridPane ui = new GridPane();
    Label healthLabel = new Label();
    Label damageLabel = new Label();
    Label itemsLabel = new Label();
    Label weaponLabel = new Label();
    Button itemButton = new Button("Pick item");
    private int uiRowIndex = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, uiRowIndex);
        addUiLabel(healthLabel, 1);
        ui.add(new Label("Damage: "),0, uiRowIndex);
        addUiLabel(damageLabel, 1);
        ui.add(new Label("Weapon: "),0,uiRowIndex);
        addUiLabel(weaponLabel, 1);

        ui.add(itemButton, 0, uiRowIndex++);
        itemButton.setFocusTraversable(false);

        itemButton.setDisable(true);
        itemButton.setOnMouseClicked(this::onMouseClicked);
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

    private void onMouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                pickUpItem();
                removeItemFromCell();
                setItemPickButton();
                refresh();
            }
        }
    }

    private void removeItemFromCell() {
        map.getPlayer().getCell().setItem(null);
    }

    private void pickUpItem() {
        Item item = map.getPlayer().getCell().getItem();
        map.getPlayer().setItem(item);
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
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case SPACE:
                moveMonsters();
                refresh();
                break;
        }
        setItemPickButton();
    }

    private void setItemPickButton() {
        itemButton.setDisable(map.getPlayer().getCell().getItem() == null);
    }

    public void moveActors(int dx, int dy) {
        map.getPlayer().move(dx, dy);
        moveMonsters();
    }


    public void moveMonsters() {
        List<Monster> monsters = map.getMonsters();
        for (Monster monster : monsters) {
            monster.move();
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
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
