package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actor.player.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private PlayerDao playerDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
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
