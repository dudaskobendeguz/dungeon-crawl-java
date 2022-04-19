package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Consumable;
import com.codecool.dungeoncrawl.logic.items.ConsumableType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Weapon;

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
