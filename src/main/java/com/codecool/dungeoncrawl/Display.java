package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.KeyType;

import com.codecool.dungeoncrawl.model.SaveSlotModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Display {
    private final int MAP_SIZE;
    Stage primaryStage;
    private final GraphicsContext context;
    private final GridPane ui;
    private final Label playerNameLabel;
    private final Label healthLabel;
    private final Label damageLabel;
    private final Label itemsLabel;
    private final Label weaponLabel;
    Scene scene;
    private int uiRowIndex = 0;

    private final Stage saveModal;
    private final Button saveButton;
    private final TextField saveInput;
    private final Button saveCancelButton;

    private final Stage loadModal;
    private final Button loadButton;
    private final Button loadCancelButton;
    private final ListView<SaveSlotModel> loadItems;

    public Display(int mapSize, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.MAP_SIZE = mapSize;
        Canvas canvas = new Canvas(
                MAP_SIZE * Tiles.TILE_WIDTH,
                MAP_SIZE * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();
        ui = new GridPane();
        playerNameLabel = new Label();
        healthLabel = new Label();
        damageLabel = new Label();
        itemsLabel = new Label();
        weaponLabel = new Label();

        saveModal = new Stage();
        saveButton = new Button("Save");
        saveInput = new TextField();
        saveCancelButton = new Button("Cancel");

        loadModal = new Stage();
        loadButton = new Button("Load Game");
        loadCancelButton = new Button("Cancel");
        loadItems = new ListView<>();


        setupUi();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

        setupModals();
    }

    private void setupModals() {
        setupSaveModal();
        setupLoadModal();
        setupImportModal();
        setupExportModal();
    }

    private void setupSaveModal() {
        saveModal.initModality(Modality.APPLICATION_MODAL);
        saveModal.setTitle("Save Game to Database");

        Label name = new Label("Name:");

        saveInput.setMaxWidth(200);
        saveButton.setMaxWidth(120);
        saveCancelButton.setMaxWidth(120);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(name, saveInput, saveButton, saveCancelButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);
        saveModal.setScene(scene);
    }

    private void setupLoadModal() {
        loadModal.initModality(Modality.APPLICATION_MODAL);
        loadModal.setTitle("Load Game from Database");

        Label select = new Label("Select a save slot to load:");

        loadButton.setMaxWidth(120);
        loadCancelButton.setMaxWidth(120);


        VBox vBox = new VBox(select, loadItems, loadButton, loadCancelButton);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        Scene scene = new Scene(vBox, 300, 300);
        loadModal.setScene(scene);
    }

    private void setupExportModal() {

    }

    private void setupImportModal() {

    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Stage getSaveModal() {
        return saveModal;
    }

    public TextField getSaveInput() {
        return saveInput;
    }

    public Button getSaveCancelButton() {
        return saveCancelButton;
    }

    public Stage getLoadModal() {
        return loadModal;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public Button getLoadCancelButton() {
        return loadCancelButton;
    }

    public ListView<SaveSlotModel> getLoadItems() {
        return loadItems;
    }

    private void setupUi() {
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


    }

    void refresh(GameMap map) {
        refreshTiles(map);
        refreshUi(map);
    }

    private void refreshUi(GameMap map) {
        Player player = map.getPlayer();
        playerNameLabel.setText(player.getName());
        healthLabel.setText(String.format("%s %s", player.getHealth(), displayHealthBar(player)));
        damageLabel.setText("" + player.getDamage());
        weaponLabel.setText(player.getWeaponName());
        drawItems(player);
    }

    private void refreshTiles(GameMap map) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, MAP_SIZE, MAP_SIZE);
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                Cell cell = map.getCell(map.getPlayerX() - (MAP_SIZE / 2) + x, map.getPlayerY() - (MAP_SIZE / 2) + y);
                if (cell == null) {
                    Tiles.drawTile(context, CellType.EMPTY.getTileId(), x, y);
                } else if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor().getTileId(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem().getTileId(), x, y);
                } else {
                    Tiles.drawTile(context, cell.getTileId(), x, y);
                }
            }
        }
    }

    private String displayHealthBar(Player player) {
        double currentHealth = player.getHealth();
        double maxHealth = player.getMaxHealth();
        double hp = (currentHealth / maxHealth) * 10;
        return "♥".repeat(Math.max(0, (int) hp));
    }

    private void drawItems(Player player) {
        itemsLabel.setText(String.format("%s%n%s", getConsumablesString(player), getKeysString(player)));
    }

    private String getKeysString(Player player) {
        StringBuilder keysString = new StringBuilder("Keys: \n");
        for (KeyType keyType : KeyType.values()) {
            int numberOfKeys = player.countKeys(keyType);
            keysString.append(String.format("%s :   %s%n", keyType.getName(), numberOfKeys));
        }
        return keysString.toString();
    }

    private String getConsumablesString(Player player) {
        StringBuilder consumables = new StringBuilder("Foods: \n");
        for (ConsumableType consumableType : ConsumableType.values()) {
            int numberOfConsumable = player.countConsumables(consumableType);
            consumables.append(String.format("%s :  %s%n", consumableType.getName(), numberOfConsumable));
        }
        return consumables.toString();
    }

    private void addUiLabel(Label label, int colIndex) {
        ui.add(label, colIndex, uiRowIndex++);
    }
}
