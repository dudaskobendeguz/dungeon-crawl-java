package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.CellModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private CellDao cellDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        cellDao = new CellDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public void saveGameMap(GameMap map) {
        saveCells(map);
    }

    private void saveCells(GameMap map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.getCell(x, y).getType().isChanged()) {
                    CellModel cellModel = new CellModel(map.getCell(x, y).getTileId(), x, y);
                    cellDao.add(cellModel);
                }
            }
        }
    }

    public void loadPlayer() {
        //TODO the given id is just a dummy data, fix it
        PlayerModel playerModel = playerDao.get(1);
        createNewPlayer(playerModel);
    }

    private Player createNewPlayer(PlayerModel playerModel) {
        //TODO implement the loading player phase
        System.out.println(playerModel);
        return null;
    }

    public List<CellModel> loadMap() {
        return null;
    }

    /**
     * Trying to make connection with the database.
     * The connection needs environment variables:
     *
     * DB_NAME: the name of the current database
     *
     * DB_USERNAME: name of the database's user
     *
     * DB_PASSWORD: the password of the database's user.
     *
     * Create these environment variables before running the program!!!
     * @return connected DataSource object
     * @throws SQLException if the connection failed
     */
    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("DB_NAME");
        String user = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
