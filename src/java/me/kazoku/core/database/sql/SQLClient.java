package me.kazoku.core.database.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Kazoku
 * @version 1.0
 * <p>
 * A SQL client is used to connect to the database and execute SQL queries.
 * It is also used to get the original object that was used to create the client.
 * @since 2022-07-01
 */
public interface SQLClient<T> {
    T getOriginal();

    SQLDriver getDriver();

    SQLSettings getSettings();

    Connection getConnection() throws SQLException;
}
