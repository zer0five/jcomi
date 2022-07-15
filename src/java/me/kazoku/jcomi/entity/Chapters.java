package me.kazoku.jcomi.entity;

import me.kazoku.core.database.sql.client.JavaSQLClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Chapters implements DataAccessObject<Chapter> {

    private JavaSQLClient client;
    private Connection connection;


    public Chapters(JavaSQLClient client) {
        this.client = client;
    }

    @Override
    public int insert(Chapter object) throws SQLException {
        String sql = "INSERT INTO [Chapter] (chapter_id, ordinal, image_url) VALUES (?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getChapterID());
            statement.setInt(2, object.getOrdinal());
            statement.setString(3, object.getImageUrl());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Chapter object) throws SQLException {
        String sql = "UPDATE [Chapter] SET chapter_id = ?, ordinal = ?, image_url = ? WHERE id = ?";
         Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getChapterID());
            statement.setInt(2, object.getOrdinal());
            statement.setString(3, object.getImageUrl());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(Chapter object) throws SQLException {
        return delete(object.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM [Chapter] WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    private ResultSet queryChapter(Object identifier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM [Chapter]");
        List<Object> where = new ArrayList<>();
        if (identifier instanceof Integer) {
            sql.append("WHERE [ID] = ?");
            where.add(identifier);
        } else if (identifier instanceof String) {
            sql.append("WHERE [Chapter_id] = ?");
            where.add(identifier);
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
    public List<Chapter> get(Object identifier) throws SQLException {
        List<Chapter> chapters = new ArrayList<>();
        try (ResultSet resultSet = queryChapter(identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
               chapters.add(new Chapter(resultSet));
            }
        }
        return chapters;
    }

    @Override
    public Optional<Chapter> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = queryChapter(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new Chapter(resultSet));
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
