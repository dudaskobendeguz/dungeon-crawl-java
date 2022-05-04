package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actor.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int fireballTimer;
    private int directionTypeId;
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
            int fireballTimer,
            int directionTypeId,
            int weaponTypeId,
            List<Integer> items,
            int x,
            int y
    ) {
        this.playerName = playerName;
        this.hp = hp;
        this.fireballTimer = fireballTimer;
        this.directionTypeId = directionTypeId;
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
        this.fireballTimer = player.getFireballTimer();
        this.directionTypeId = player.getDirection().getID();
        this.weaponTypeId = player.getWeaponTitleId();
        items = player.getItems().stream()
                .map(Drawable::getTileId)
                .collect(Collectors.toList());
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHp() {
        return hp;
    }

    public int getFireballTimer() {
        return fireballTimer;
    }

    public int getDirectionTypeId() {
        return directionTypeId;
    }

    public int getWeaponTypeId() {
        return weaponTypeId;
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
