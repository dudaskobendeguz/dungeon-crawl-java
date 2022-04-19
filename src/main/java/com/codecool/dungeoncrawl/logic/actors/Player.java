package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private Weapon weapon;
    private static class Inventory {
        private final List<Item> items = new ArrayList<>();

        public void setItem(Item item) {
            items.add(item);
        }

        public int countConsumables(ConsumableType consumableType) {
            int consumableCounter = 0;
            for (Item item : items) {
                if (item instanceof Consumable) {
                    Consumable consumable = (Consumable) item;
                    if (consumable.getConsumableType().equals(consumableType)) {
                        consumableCounter++;
                    }
                }
            }
            return consumableCounter;
        }

        public int countKeys(KeyType keyType) {
            int keyCounter = 0;
            for (Item item : items) {
                if (item instanceof Key) {
                    Key key = (Key) item;
                    if (key.getKeyType().equals(keyType)) {
                        keyCounter++;
                    }
                }
            }
            return keyCounter;
        }
    }

    public Player(Cell cell) {
        super(cell);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getTileName() {
        return "player";
    }
}
