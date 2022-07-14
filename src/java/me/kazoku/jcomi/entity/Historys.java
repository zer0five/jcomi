package me.kazoku.jcomi.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.kazoku.core.database.sql.client.JavaSQLClient;

public class Historys implements DataAccessObject<History> {

    private JavaSQLClient client;
    private Connection connection;

    public Historys(JavaSQLClient client) {
        this.client = client;
    }

    @Override
    public int insert(History object) throws SQLException {
        String sql = "INSERT INTO [History] (Chapter_ID, Account_ID, Read_Date) VALUES (?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getChapterId());
            statement.setInt(2, object.getAccountId());
            statement.setLong(3, object.getReadDate());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(History object) throws SQLException {
        String sql = "UPDATE [History] SET Chapter_ID = ?, Account_ID = ?,  Read_Date = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getChapterId());
            statement.setInt(2, object.getAccountId());
            statement.setLong(3, object.getReadDate());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(History object) throws SQLException {
        return delete(object.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM [History] WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    private ResultSet queryHistory(Object identifier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM [History]");
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
    public List<History> get(Object identifier) throws SQLException {
        List<History> historys = new ArrayList<>();
        try (ResultSet resultSet = queryHistory(identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
                historys.add(new History(resultSet));
            }
        }
        return historys;
    }

    @Override
    public Optional<History> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = queryHistory(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new History(resultSet));
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
