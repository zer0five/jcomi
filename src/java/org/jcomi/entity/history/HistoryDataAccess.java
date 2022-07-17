package org.jcomi.entity.history;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryDataAccess extends DataAccessObject<History> {

    public HistoryDataAccess() {
        super();
    }

    @Override
    protected History createObject(ResultSet resultSet) throws SQLException {
        return new History(resultSet);
    }

    @Override
    public int insert(History object) throws SQLException {
        return sqlUpdate(
            "INSERT INTO [History] " +
                "([Account_ID], [Chapter_ID]) " +
                "VALUES (?, ?)",
            object.getAccountId(),
            object.getChapterId()
        );
    }

    @Override
    public Object insertAndGetIdentifier(History object) throws SQLException {
        ResultSet resultSet = sqlQuery(
            "INSERT INTO [History] " +
                "([Account_ID], [Chapter_ID]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?)",
            object.getAccountId(),
            object.getChapterId()
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
    public int update(History object) throws SQLException {
        return sqlUpdate(
            "UPDATE [History] " +
                "SET [Account_ID] = ?, [Chapter_ID] = ? " +
                "WHERE [ID] = ?",
            object.getAccountId(),
            object.getChapterId(),
            object.getId()
        );
    }

    @Override
    public int delete(History object) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [History] WHERE [ID] = ?",
            object.getId()
        );
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        if (identifier instanceof Integer) {
            return sqlUpdate("DELETE FROM [History] WHERE [ID] = ?", identifier);
        } else {
            throw new SQLException("Invalid identifier");
        }
    }

    @Override
    protected ResultSet selectById(Object identifier) throws SQLException {
        if (identifier instanceof Integer) {
            return sqlQuery(
                "SELECT * FROM [History] WHERE [ID] = ?",
                identifier
            );
        } else {
            throw new SQLException("Invalid identifier");
        }
    }
}
