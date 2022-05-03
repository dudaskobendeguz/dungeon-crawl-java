package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    int add(GameState state);
    void update(GameState state);
    GameState get(int gameStateId);
    List<GameState> getAll();
}
