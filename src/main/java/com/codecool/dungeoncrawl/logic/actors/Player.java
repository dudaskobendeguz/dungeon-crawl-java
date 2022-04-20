package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actor.Actor;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private Weapon weapon = new Weapon(null, WeaponType.FIST);

    public Weapon getWeapon() {
        return weapon;
    }

    private static class Inventory {
        private static final List<Item> items = new ArrayList<>();

        public static void setItem(Item item) {
            items.add(item);
        }

        public static int countConsumables(ConsumableType consumableType) {
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

        public static int countKeys(KeyType keyType) {
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

        public static List<Key> getKeys() {
            List<Key> keys = new ArrayList<>();
            for (Item item : items) {
                if (item instanceof Key) {
                    keys.add((Key) item);
                }
            }
            return keys;
        }

        public static void removeItem(Item removableItem) {
            items.remove(removableItem);
        }
    }

    public Player(Cell cell) {
        super(cell, 10, 5);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (isValidStep(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        tryToAttack();
    }

    private List<Monster> getNeighborMonsters() {
        List<Monster> monsters = new ArrayList<>();
        List<Cell> cells = cell.getNonDiagonalNeighbors();
        for (Cell cell : cells) {
            if (cell != null) {
                Actor actor = cell.getActor();
                if (actor != null && actor.isMonster()) {
                    monsters.add((Monster) actor);
                }
            }
        }
        return monsters;
    }

    public void tryToAttack() {
        List<Monster> monsters = getNeighborMonsters();
        for (Monster monster : monsters) {
            attackMonster(monster);
        }
    }

    public void attackMonster(Monster monster) {
        monster.takeDamage(damage);
        if (monster.isAboutToDie()) {
            monster.die();
        } else {
            takeDamage(monster.getDamage());
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setItem(Item item) {
        if (item instanceof Weapon) {
            setWeapon((Weapon) item);
        } else {
            Inventory.setItem(item);
        }
    }

    public void tryToUseKey() {
        List<Cell> nonDiagonalNeighbors = getCell().getNonDiagonalNeighbors();
        List<Key> keys = Inventory.getKeys();
        for (Cell neighbourCell : nonDiagonalNeighbors) {
            CellType cellType = neighbourCell.getType();
            if (cellType.isOpenable()) {
                Key key = keyForDoor(cellType, keys);
                if (key != null) {
                    openDoor(neighbourCell, key);
                    Inventory.removeItem(key);
                }
            }
        }
    }

    private void openDoor(Cell neighbourCell, Key key) {
        neighbourCell.setType(key.getOpenedDoor());
    }


    private Key keyForDoor(CellType cellType, List<Key> keys) {
        for (Key key : keys) {
            if (key.getClosedDoorType().equals(cellType)) {
                return key;
            }
        }
        return null;
    }

    public int getWeaponDamage() {
        return weapon.getDamage();
    }

    public int countKeys(KeyType keyType) {
        return Inventory.countKeys(keyType);
    }

    public int countConsumables(ConsumableType consumableType) {
        return Inventory.countConsumables(consumableType);
    }

    public String getTileName() {
        return "player";
    }
}
