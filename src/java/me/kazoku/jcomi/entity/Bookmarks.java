package me.kazoku.jcomi.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.kazoku.core.database.sql.client.JavaSQLClient;

public class Bookmarks implements DataAccessObject<Comment> {

    private JavaSQLClient client;
    private Connection connection;

    public Bookmarks(JavaSQLClient client) {
        this.client = client;
    }

    @Override
    public int insert(Comment object) throws SQLException {
        String sql = "INSERT INTO [Comment] (Comic_ID, Account_ID) VALUES (?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getComicId());
            statement.setInt(2, object.getAccountId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Comment object) throws SQLException {
        String sql = "UPDATE [Comment] SET Comic_ID = ?, Account_ID = ?, Content = ?, Post_Date = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getComicId());
            statement.setInt(2, object.getAccountId());
            statement.setString(3, object.getContent());
            statement.setLong(4, object.getPostedDate());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(Comment object) throws SQLException {
        return delete(object.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM [Comment] WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    private ResultSet queryComment(Object identifier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM [Comment]");
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
    public List<Comment> get(Object identifier) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (ResultSet resultSet = queryComment(identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
                comments.add(new Comment(resultSet));
            }
        }
        return comments;
    }

    @Override
    public Optional<Comment> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = queryComment(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new Comment(resultSet));
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
