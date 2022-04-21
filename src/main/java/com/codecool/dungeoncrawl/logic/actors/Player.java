package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Chest;
import com.codecool.dungeoncrawl.logic.actor.Actor;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private Weapon weapon = new Weapon(null, WeaponType.FIST);
    private final Inventory inventory = new Inventory();

    public Weapon getWeapon() {
        return weapon;
    }

    public void tryToPickUpItem() {
        if (isItemOnPlayersCell()) {
            pickUpItem();
        }
    }

    private boolean isItemOnPlayersCell() {
        return getCell().getItem() != null;
    }

    private void pickUpItem() {
        Item item = getCell().getItem();
        setItem(item);
        getCell().setItem(null);
    }

    public boolean isTryingToSwitchLevel() {
        return cell.getType().isLevelSwitcher();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

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

        public List<Key> getKeys() {
            List<Key> keys = new ArrayList<>();
            for (Item item : items) {
                if (item instanceof Key) {
                    keys.add((Key) item);
                }
            }
            return keys;
        }

        public void removeItem(Item removableItem) {
            items.remove(removableItem);
        }
    }

    public Player(Cell cell) {
        super(cell, 10, 1);
    }

    public Player() {
        super(10, 5);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (isValidStep(nextCell)) {
            stepOne(nextCell);
        }
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
            inventory.setItem(item);
        }
    }

    public void tryToUseKey() {
        List<Cell> nonDiagonalNeighbors = getCell().getNonDiagonalNeighbors();
        List<Key> keys = inventory.getKeys();
        for (Cell neighbourCell : nonDiagonalNeighbors) {
            CellType cellType = neighbourCell.getType();
            if (cellType.isOpenable()) {
                Key key = keyForOpenableCell(cellType, keys);
                if (key != null) {
                    unlockOpenableCell(neighbourCell, key);
                    inventory.removeItem(key);
                }
            }
        }
    }

    private void unlockOpenableCell(Cell cell, Key key) {
        if(cell instanceof Chest && cell.getType().equals(CellType.CHEST_CLOSED)) {
            Chest chest = (Chest) cell;
            chest.dropConsumable();
        }
        cell.setType(key.getOpenedDoor());
    }


    private Key keyForOpenableCell(CellType cellType, List<Key> keys) {
        for (Key key : keys) {
            if (key.getClosedCellType().equals(cellType)) {
                return key;
            }
        }
        return null;
    }

    public int getWeaponDamage() {
        return weapon.getDamage();
    }

    public int countKeys(KeyType keyType) {
        return inventory.countKeys(keyType);
    }

    public int countConsumables(ConsumableType consumableType) {
        return inventory.countConsumables(consumableType);
    }

    public String getTileName() {
        return weapon.getPlayerSkin();
    }
}
