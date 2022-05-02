package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actor.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int maxHp;
    private int fireballTimer;
    private int damage;
    private String directionType;
    private int cellType;
    private int weaponType;
    private List<Integer> items;

    private int x;
    private int y;

    /**
     * This constructor create an instance by the datas from the database.
     */
    public PlayerModel(
            String playerName,
            int hp,
            int maxHp,
            int fireballTimer,
            int damage,
            String directionType,
            int cellType,
            int weaponType,
            Integer[] items,
            int x,
            int y
    ) {
        this.playerName = playerName;
        this.hp = hp;
        this.maxHp = maxHp;
        this.fireballTimer = fireballTimer;
        this.damage = damage;
        this.directionType = directionType;
        this.cellType = cellType;
        this.weaponType = weaponType;
        this.items = new ArrayList<>(List.of(items));
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.hp = player.getHealth();
        this.maxHp = player.getMaxHealth();
        this.fireballTimer = player.getFireballTimer();
        this.damage = player.getDamage();
        this.directionType = player.getDirection().name();
        this.cellType = player.getCellTitleId();
        this.weaponType = player.getWeaponTitleId();
        items = player.getItems().stream()
                .map(Drawable::getTileId)
                .collect(Collectors.toList());
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getFireballTimer() {
        return fireballTimer;
    }

    public void setFireballTimer(int fireballTimer) {
        this.fireballTimer = fireballTimer;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getDirectionType() {
        return directionType;
    }

    public void setDirectionType(String directionType) {
        this.directionType = directionType;
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public int getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
