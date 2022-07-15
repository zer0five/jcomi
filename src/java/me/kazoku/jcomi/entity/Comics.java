/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.kazoku.jcomi.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.kazoku.core.database.sql.client.JavaSQLClient;

/**
 *
 * @author Radkaim
 */
public class Comics implements DataAccessObject<Comic> {

    private JavaSQLClient client;
    private Connection connection;

    public Comics(JavaSQLClient client) {
        this.client = client;
    }

    @Override
    public int insert(Comic object) throws SQLException {
        String sql = "INSERT INTO [Comic] (name, alt_name, author, cover, views, description) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getName());
            statement.setString(2, object.getAltName());
            statement.setString(3, object.getAuthor());
            statement.setString(4, object.getCover());
            statement.setInt(5, object.getViews());
            statement.setString(6, object.getDescription());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Comic object) throws SQLException {
        String sql = "UPDATE [Comic] SET name = ?, alt_name = ?, author = ?, cover = ?, views = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getName());
            statement.setString(2, object.getAltName());
            statement.setString(3, object.getAuthor());
            statement.setString(4, object.getCover());
            statement.setInt(5, object.getViews());
            statement.setString(6, object.getDescription());

            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(Comic object) throws SQLException {
        return delete(object.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM [Account] WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    private ResultSet queryComic(Object identifier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM [Comic]");
        List<Object> where = new ArrayList<>();
        if (identifier instanceof Integer) {
            sql.append("WHERE [ID] = ?");
            where.add(identifier);
        }
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql.toString());
        for (int i = 0; i < where.size(); i++) {
            statement.setObject(i + 1, where.get(i));
        }
        return statement.executeQuery();
    }

    @Override
    public List<Comic> get(Object identifier) throws SQLException {
        List<Comic> comics = new ArrayList<>();
        try (ResultSet resultSet = queryComic(identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
                comics.add(new Comic(resultSet));
            }
        }
        return comics;
    }

    @Override
    public Optional<Comic> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = queryComic(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new Comic(resultSet));
            }
        }
        return Optional.empty();
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = client.getConnection();
        }
        return connection;
    }
}
