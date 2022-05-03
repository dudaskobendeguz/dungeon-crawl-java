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
    private int directionTypeId;
    private int cellTypeId;
    private int weaponTypeId;
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
            int directionTypeId,
            int cellTypeId,
            int weaponTypeId,
            List<Integer> items,
            int x,
            int y
    ) {
        this.playerName = playerName;
        this.hp = hp;
        this.maxHp = maxHp;
        this.fireballTimer = fireballTimer;
        this.damage = damage;
        this.directionTypeId = directionTypeId;
        this.cellTypeId = cellTypeId;
        this.weaponTypeId = weaponTypeId;
        this.items = items;
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
        this.directionTypeId = player.getDirection().getID();
        this.cellTypeId = player.getCellTitleId();
        this.weaponTypeId = player.getWeaponTitleId();
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

    public int getDirectionTypeId() {
        return directionTypeId;
    }

    public void setDirectionTypeId(int directionTypeId) {
        this.directionTypeId = directionTypeId;
    }

    public int getCellTypeId() {
        return cellTypeId;
    }

    public void setCellTypeId(int cellTypeId) {
        this.cellTypeId = cellTypeId;
    }

    public int getWeaponTypeId() {
        return weaponTypeId;
    }

    public void setWeaponTypeId(int weaponTypeId) {
        this.weaponTypeId = weaponTypeId;
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
