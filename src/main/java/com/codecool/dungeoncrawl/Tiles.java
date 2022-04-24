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
    private static final int numberOfrTiles = 1024;
    private static final int rowLength = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<Integer, Tile> tileMap = new HashMap<>();
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
        for (int tileID = 0; tileID < numberOfrTiles; tileID++) {
            int row = tileID / (rowLength);
            int col = tileID % (rowLength);
//            System.out.printf("%s: (%s,%s)%n", tileID, row, col);
            tileMap.put(tileID, new Tile(col,row));
        }
    }

    public static void drawTile(GraphicsContext context, int tileId, int x, int y) {
        Tile tile = tileMap.get(tileId);
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
