package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actor.monsters.MonsterType;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.KeyType;
import com.codecool.dungeoncrawl.logic.items.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int col, int row) {
            x = col * (TILE_WIDTH + 2);
            y = row * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        // Player Skins
        tileMap.put(WeaponType.FIST.getPlayerSkin(), new Tile(25, 0));
        tileMap.put(WeaponType.KNIFE.getPlayerSkin(), new Tile(23, 0));
        tileMap.put(WeaponType.SWORD.getPlayerSkin(), new Tile(27, 0));
        tileMap.put(WeaponType.AXE.getPlayerSkin(), new Tile(26, 0));
        tileMap.put(WeaponType.HAMMER.getPlayerSkin(), new Tile(28, 1));

        // Monster Skins
        tileMap.put(MonsterType.SKELETON.getTileName(), new Tile(29, 6));
        tileMap.put("chicken", new Tile(26 ,7));
        tileMap.put("slime", new Tile(27, 8));
        tileMap.put("robot", new Tile(26, 3));

        // Cell Skins
        tileMap.put(CellType.EMPTY.getTileName(), new Tile(0, 0));
        tileMap.put(CellType.WALL.getTileName(), new Tile(10, 17));
        tileMap.put(CellType.FLOOR_1.getTileName(), new Tile(3, 0));
        tileMap.put(CellType.FLOOR_2.getTileName(), new Tile(1, 0));
        tileMap.put(CellType.GRAVE_1.getTileName(), new Tile(1, 14));
        tileMap.put(CellType.GRAVE_2.getTileName(), new Tile(0, 14));
        tileMap.put(CellType.BONES.getTileName(), new Tile(0, 15));
        tileMap.put(CellType.CANDLES.getTileName(), new Tile(5, 15));
        tileMap.put(CellType.HOUSE_1.getTileName(), new Tile(4, 20));
        tileMap.put(CellType.LEVEL_SWITCH_HOUSE.getTileName(), new Tile(7, 20));
        tileMap.put(CellType.HOUSE_2.getTileName(), new Tile(1, 21));

        tileMap.put(CellType.TREE_1.getTileName(), new Tile(0, 1));
        tileMap.put(CellType.TREE_2.getTileName(), new Tile(1, 1));
        tileMap.put(CellType.TREE_3.getTileName(), new Tile(2, 1));
        tileMap.put(CellType.TREE_4.getTileName(), new Tile(3, 1));
        tileMap.put(CellType.TREE_5.getTileName(), new Tile(4, 1));
        tileMap.put(CellType.TREE_6.getTileName(), new Tile(5, 1));

        tileMap.put(CellType.TREE_7.getTileName(), new Tile(0, 2));
        tileMap.put(CellType.TREE_8.getTileName(), new Tile(1, 2));
        tileMap.put(CellType.TREE_9.getTileName(), new Tile(2, 2));
        tileMap.put(CellType.TREE_10.getTileName(), new Tile(3, 2));
        tileMap.put(CellType.TREE_11.getTileName(), new Tile(4, 2));
        tileMap.put(CellType.TREE_12.getTileName(), new Tile(5, 2));

        tileMap.put(CellType.TREE_13.getTileName(), new Tile(5, 0));
        tileMap.put(CellType.TREE_14.getTileName(), new Tile(6, 0));
        tileMap.put(CellType.TREE_15.getTileName(), new Tile(7, 0));
        tileMap.put(CellType.TREE_16.getTileName(), new Tile(19, 5));
        tileMap.put(CellType.TREE_17.getTileName(), new Tile(20, 5));
        tileMap.put(CellType.TREE_18.getTileName(), new Tile(13, 6));
        tileMap.put(CellType.TREE_19.getTileName(), new Tile(14, 6));
        tileMap.put(CellType.TREE_20.getTileName(), new Tile(15, 6));
        tileMap.put(CellType.TREE_21.getTileName(), new Tile(16, 6));
        tileMap.put(CellType.TREE_22.getTileName(), new Tile(17, 6));
        tileMap.put(CellType.TREE_23.getTileName(), new Tile(18, 6));
        tileMap.put(CellType.TREE_24.getTileName(), new Tile(19, 6));
        tileMap.put(CellType.TREE_25.getTileName(), new Tile(20, 6));

        tileMap.put(CellType.SIMPLE_DOOR_OPENED.getTileName(), new Tile(6, 9));
        tileMap.put(CellType.SIMPLE_DOOR_CLOSED.getTileName(), new Tile(3, 9));
        tileMap.put(CellType.LEVEL_SWITCH_DOOR_CLOSED.getTileName(), new Tile(0, 9));
        tileMap.put(CellType.LEVEL_SWITCH_DOOR_OPENED.getTileName(), new Tile(1, 9));
        tileMap.put(CellType.CHEST_CLOSED.getTileName(), new Tile(8, 6));
        tileMap.put(CellType.CHEST_OPENED.getTileName(), new Tile(9, 6));

        // Item skins
        //      Consumable skins
        tileMap.put(ConsumableType.APPLE.getTileName(), new Tile(15, 29));
        tileMap.put(ConsumableType.BREAD.getTileName(), new Tile(15, 28));
        tileMap.put(ConsumableType.MEAT.getTileName(), new Tile(17, 28));
        //      Key skins
        tileMap.put(KeyType.CHEST_KEY.getTileName(), new Tile(16, 23));
        tileMap.put(KeyType.SIMPLE_DOOR_KEY.getTileName(), new Tile(18, 23));
        tileMap.put(KeyType.LEVEL_KEY.getTileName(), new Tile(17 , 23));
        //      Weapon skins
        tileMap.put(WeaponType.KNIFE.getTileName(), new Tile(0, 28));
        tileMap.put(WeaponType.SWORD.getTileName(), new Tile(0, 30));
        tileMap.put(WeaponType.AXE.getTileName(), new Tile(10, 30));
        tileMap.put(WeaponType.HAMMER.getTileName(), new Tile(5, 29));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
