package org.jcomi.entity.account;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDataAccess extends DataAccessObject<Account> {

    public AccountDataAccess() {
        super();
    }

    @Override
    protected Account createObject(ResultSet resultSet) throws SQLException {
        return new Account(resultSet);
    }

    @Override
    public int insert(Account object) throws SQLException {
        return sqlUpdate(
            "INSERT INTO [Account] " +
                "([Username], [Password], [Email], [Display_Name], [Banned], [Is_Admin], [Is_Uploader]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            object.getUsername(),
            object.getPassword(),
            object.getEmail(),
            object.getDisplayName(),
            object.isBanned(),
            object.isAdmin(),
            object.isUploader()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Account object) throws SQLException {
        ResultSet resultSet = sqlQuery(
            "INSERT INTO [Account] " +
                "([Username], [Password], [Email], [Display_Name], [Banned], [Is_Admin], [Is_Uploader]) " +
                "OUTPUT inserted.ID " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            object.getUsername(), object.getPassword(), object.getEmail(),
            object.getDisplayName(), object.isBanned(), object.isAdmin(),
            object.isUploader()
        );
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            object.setId(id); // sync object with new id
            return id;
        } else {
            throw new SQLException("No result returned");
        }
    }

    @Override
    public int update(Account object) throws SQLException {
        int result = sqlUpdate(
            "UPDATE [Account] " +
                "SET " +
                "[Username] = ?, [Password] = ?, [Email] = ?, [Display_Name] = ?, " +
                "[Banned] = ?, [Is_Admin] = ?, [Is_Uploader] = ? " +
                "WHERE [ID] = ?",
            object.getUsername(), object.getPassword(), object.getEmail(), object.getDisplayName(),
            object.isBanned(), object.isAdmin(), object.isUploader(),
            object.getId()
        );
        if (result != 0) {
            ResultSet newData = selectById(object.getId());
            if (newData.next()) {
                object.sync(newData);
            } else {
                throw new SQLException("No result returned");
            }
        }
        return result;
    }

    @Override
    public int delete(Account object) throws SQLException {
        return delete(object.getId());
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        if (identifier instanceof Integer) {
            return sqlUpdate("DELETE FROM [Account] WHERE [ID] = ?", (Integer) identifier);
        } else {
            throw new IllegalArgumentException("Identifier must be an integer");
        }
    }

    public ResultSet selectById(Object identifier) throws SQLException {
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
        return sqlQuery(sql.toString(), where.toArray());
    }

    @Override
    public List<Account> get(Object identifier) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (ResultSet resultSet = selectById(identifier)) {
            while (resultSet.next()) {
                accounts.add(new Account(resultSet));
            }
        }
        return accounts;
    }

    @Override
    public Optional<Account> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = selectById(identifier)) {
            if (resultSet.next()) {
                return Optional.of(new Account(resultSet));
            }
        }
        return Optional.empty();
    }

}
