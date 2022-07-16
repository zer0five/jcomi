package org.jcomi.entity;

import me.kazoku.core.database.sql.client.JavaSQLClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DataAccessObject<T extends DataTransferObject> {

    protected JavaSQLClient client;
    private Connection connection;

    protected DataAccessObject(JavaSQLClient client) {
        this.client = client;
    }

    protected Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = client.getConnection();
        }
        return connection;
    }

    protected abstract T createObject(ResultSet resultSet) throws SQLException;

    /**
     * Inserts a new object into the database.
     *
     * @param object The object to insert.
     * @return Number of rows affected.
     * @throws SQLException
     */
    public abstract int insert(T object) throws SQLException;

    /**
     * Inserts a new object into the database.
     *
     * @param object The object to insert.
     * @return Identifier of the inserted object.
     * @throws SQLException
     */
    public abstract Object insertAndGetIdentifier(T object) throws SQLException;

    /**
     * Updates an existing object in the database.
     *
     * @param object The object to update.
     * @return Number of rows affected.
     * @throws SQLException
     */
    public abstract int update(T object) throws SQLException;

    /**
     * Deletes an object from the database.
     *
     * @param object The object to delete.
     * @return Number of rows affected.
     * @throws SQLException
     */
    public abstract int delete(T object) throws SQLException;

    /**
     * Deletes an object from the database.
     *
     * @param identifier The identifier of the object to delete.
     * @return Number of rows affected.
     * @throws SQLException
     */
    public abstract int delete(Object identifier) throws SQLException;

    /**
     * Query the database.
     *
     * @param identifier The identifier of the object to query.
     * @return The result set.
     * @throws SQLException
     */
    protected abstract ResultSet query(Object identifier) throws SQLException;

    /**
     * Get a list of object from the database.
     *
     * @param identifier The identifier of the object to delete.
     * @return A list of object.
     * @throws SQLException
     */
    public List<T> get(Object identifier) throws SQLException {
        ResultSet resultSet = query(identifier);
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(createObject(resultSet));
        }
        return list;
    }

    /**
     * Get an object from the database.
     *
     * @param identifier The identifier of the object to delete.
     * @return An object.
     * @throws SQLException
     */
    public Optional<T> getOne(Object identifier) throws SQLException {
        ResultSet resultSet = query(identifier);
        if (resultSet.next()) {
            return Optional.of(createObject(resultSet));
        }
        return Optional.empty();
    }

    /**
     * Execute a update query.
     *
     * @param sql        The sql query.
     * @param parameters The parameters of the query.
     * @return Number of rows affected.
     * @throws SQLException
     */
    protected int update(String sql, Object... parameters) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            return statement.executeUpdate();
        }
    }

    /**
     * Execute query.
     *
     * @param sql        The sql query.
     * @param parameters The parameters to bind to the query.
     * @return The result set.
     * @throws SQLException
     */
    protected ResultSet query(String sql, Object... parameters) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
        return statement.executeQuery();
    }

}
