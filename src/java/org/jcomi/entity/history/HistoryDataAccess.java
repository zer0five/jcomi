package org.jcomi.entity.history;

import me.kazoku.core.database.sql.client.JavaSQLClient;
import org.jcomi.entity.DataAccessObject;
import org.jcomi.util.DatabaseUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryDataAccess extends DataAccessObject<History> {

    private static HistoryDataAccess instance;

    private HistoryDataAccess(JavaSQLClient client) {
        super(client);
    }

    public static HistoryDataAccess getInstance() {
        if (instance == null) {
            instance = new HistoryDataAccess(DatabaseUtil.createClient());
        }
        return instance;
    }

    @Override
    protected History createObject(ResultSet resultSet) throws SQLException {
        return new History(resultSet);
    }

    @Override
    public int insert(History object) throws SQLException {
        return update(
            "INSERT INTO [History] " +
                "([Account_ID], [Chapter_ID], [Read_Date]) " +
                "VALUES (?, ?, ?)",
            object.getAccountId(),
            object.getChapterId(),
            object.getReadDate()
        );
    }

    @Override
    public Object insertAndGetIdentifier(History object) throws SQLException {
        try (ResultSet resultSet = query(
            "INSERT INTO [History] " +
                "([Account_ID], [Chapter_ID], [Read_Date]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?, ?)",
             object.getAccountId(),
            object.getChapterId(),
            object.getReadDate()
        )) {
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                object.setId(id); // sync object with new id
                return id;
            } else {
                throw new SQLException("No result returned");
            }
        }
    }

    @Override
    public int update(History object) throws SQLException {
        int result = update(
            "UPDATE [History] SET [Account_ID] = ?, [Chapter_ID] = ?, [Read_Date] = ? WHERE [ID] = ?",
            object.getAccountId(),
            object.getChapterId(),
            object.getReadDate()
        );
        if (result != 0) {
            object.sync(query(object.getId()));
        }
        return result;
    }

    @Override
    public int delete(History object) throws SQLException {
        return delete(object.getId());
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        if (identifier instanceof Integer) {
            return update("DELETE FROM [History] WHERE [ID] = ?", identifier);
        } else {
            throw new IllegalArgumentException("Identifier must be an Integer");
        }
    }

    @Override
    protected ResultSet query(Object identifier) throws SQLException {
        return query("SELECT * FROM [History] WHERE [ID] = ?", identifier);
    }

    @Override
    public List<History> get(Object identifier) throws SQLException {
        List<History> historys = new ArrayList<>();
        try (ResultSet resultSet = query("SELECT * FROM [History] WHERE [Account_ID] = ?", identifier)) {
            while (resultSet.next()) {
                historys.add(new History(resultSet));
            }
        }
        return historys;
    }

    @Override
    public Optional<History> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = query(identifier)) {
            if (resultSet.next()) {
                return Optional.of(new History(resultSet));
            }
        }
        return Optional.empty();
    }
}
