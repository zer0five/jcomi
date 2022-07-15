package me.kazoku.jcomi.entity;

import me.kazoku.core.database.sql.client.JavaSQLClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Accounts implements DataAccessObject<Account> {

    private JavaSQLClient client;
    private Connection connection;


    public Accounts(JavaSQLClient client) {
        this.client = client;
    }

    @Override
    public int insert(Account object) throws SQLException {
        String sql = "INSERT INTO [Account] (username, password, email, display_name, avatar, banned, is_admin, is_uploader) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getDisplayName());
            statement.setString(5, object.getAvatar());
            statement.setBoolean(6, object.isBanned());
            statement.setBoolean(7, object.isAdmin());
            statement.setBoolean(8, object.isUploader());
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(Account object) throws SQLException {
        String sql = "UPDATE [Account] SET password = ?, display_name = ?, avatar = ?, banned = ?, is_admin = ?, is_uploader = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getPassword());
            statement.setString(2, object.getDisplayName());
            statement.setString(3, object.getAvatar());
            statement.setBoolean(4, object.isBanned());
            statement.setBoolean(5, object.isAdmin());
            statement.setBoolean(6, object.isUploader());
            statement.setInt(7, object.getId());
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete(Account object) throws SQLException {
        return delete(object.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM [Account] WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    private ResultSet queryAccount(Object identifier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM [Account]");
        List<Object> where = new ArrayList<>();
        if (identifier instanceof Integer) {
            sql.append("WHERE [ID] = ?");
            where.add(identifier);
        } else if (identifier instanceof String) {
            sql.append("WHERE [Username] = ? OR [Email] = ?");
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
    public List<Account> get(Object identifier) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (ResultSet resultSet = queryAccount(identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
                accounts.add(new Account(resultSet));
            }
        }
        return accounts;
    }

    @Override
    public Optional<Account> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = queryAccount(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new Account(resultSet));
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
