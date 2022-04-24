package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actor.monsters.MonsterType;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.KeyType;
import com.codecool.dungeoncrawl.logic.items.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public class Tiles {
    public static final int TILE_WIDTH = 32;
    private static final int NUMBER_OF_TILES = 1024;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<Integer, Tile> tileMap = new HashMap<>();
    public static final Map<Integer, TileType> tileTypeMap = new HashMap<>();
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
        tileTypeMap.put(-1, CellType.EMPTY);
        for (int tileId = 0; tileId < NUMBER_OF_TILES; tileId++) {
            int row = tileId / (TILE_WIDTH);
            int col = tileId % (TILE_WIDTH);
//            System.out.printf("%s: (%s,%s)%n", tileId, row, col);
            tileMap.put(tileId, new Tile(col,row));

            TileType tileType = getTileTypeById(tileId);
            tileTypeMap.put(tileId, tileType);
        }
    }

    private static List<TileType> loadTileTypes() {
        List<TileType> tileTypes = new ArrayList<>();
        tileTypes.addAll(Arrays.asList(CellType.values()));
        tileTypes.addAll(Arrays.asList(MonsterType.values()));
        tileTypes.addAll(Arrays.asList(ConsumableType.values()));
        tileTypes.addAll(Arrays.asList(KeyType.values()));
        tileTypes.addAll(Arrays.asList(WeaponType.values()));
        return tileTypes;
    }

    private static TileType getTileTypeById(int id) {
        List<TileType> tileTypes = loadTileTypes();
        for (TileType tileType : tileTypes) {
            if (tileType.getTileId() == id) {
                return tileType;
            }
        }
        return null;
    }

    public static void drawTile(GraphicsContext context, int tileId, int x, int y) {
        Tile tile = tileMap.get(tileId);
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
