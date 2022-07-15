package me.kazoku.jcomi.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.kazoku.core.database.sql.client.JavaSQLClient;

public class Bookmarks implements DataAccessObject<Bookmark> {

    private JavaSQLClient client;
    private Connection connection;

    public Bookmarks(JavaSQLClient client) {
        this.client = client;
    }

    @Override
    public int insert(Bookmark object) throws SQLException {
        String sql = "INSERT INTO [Bookmarks] (Comic_ID, Account_ID) VALUES (?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getComicId());
            statement.setInt(2, object.getAccountId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Bookmark object) throws SQLException {
        String sql = "UPDATE [Bookmarks] SET Comic_ID = ?, Account_ID = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getComicId());
            statement.setInt(2, object.getAccountId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(Bookmark object) throws SQLException {
        return delete(object.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM [Bookmarks] WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    private ResultSet queryBookmark(Object identifier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM [Bookmarks]");
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
    public List<Bookmark> get(Object identifier) throws SQLException {
        List<Bookmark> bookmarks = new ArrayList<>();
        try (ResultSet resultSet = queryBookmark(identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
                bookmarks.add(new Bookmark(resultSet));
            }
        }
        return bookmarks;
    }

    @Override
    public Optional<Bookmark> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = queryBookmark(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new Bookmark(resultSet));
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
