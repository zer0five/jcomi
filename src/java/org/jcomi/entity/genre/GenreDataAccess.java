package org.jcomi.entity.genre;

import me.kazoku.core.database.sql.client.JavaSQLClient;
import org.jcomi.entity.DataAccessObject;
import org.jcomi.entity.account.Account;
import org.jcomi.util.DatabaseUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDataAccess extends DataAccessObject<Genre> {

    private static GenreDataAccess instance;

    public static GenreDataAccess getInstance() {
        if (instance == null) {
            instance = new GenreDataAccess(DatabaseUtil.createClient());
        }
        return instance;
    }

    private GenreDataAccess(JavaSQLClient client) {
        super(client);
    }

    @Override
    protected Genre createObject(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet);
    }

    @Override
    public int insert(Genre object) throws SQLException {
        return update(
            "INSERT INTO [Genre] " +
                "([Genre], [Description]) " +
                "VALUES (?, ?)",
            object.getId(),
            object.getDescription()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Genre object) throws SQLException {
        try (ResultSet resultSet = query(
            "INSERT INTO [Genre] " +
                "([Genre], [Description]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?)",
            object.getId(),
            object.getDescription()
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
    public int update(Genre object) throws SQLException {
        return update(
            "UPDATE [Genre] " +
                "SET [Genre] = ?, [Description] = ? " +
                "WHERE [ID] = ?",
            object.getId(),
            object.getDescription()
        );
    }

    @Override
    public int delete(Genre object) throws SQLException {
        return update(
            "DELETE FROM [Genre] " +
                "WHERE [ID] = ?",
            object.getId()
        );
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        return update(
            "DELETE FROM [Genre] " +
                "WHERE [ID] = ?",
            identifier
        );
    }

    @Override
    protected ResultSet query(Object identifier) throws SQLException {
        return query(
            "SELECT * FROM [Genre] " +
                "WHERE [ID] = ?",
            identifier
        );
    }

    @Override
    public List<Genre> get(Object identifier) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (ResultSet resultSet = query(identifier)) {
            while (resultSet.next()) {
                genres.add(new Genre(resultSet));
            }
        }
        return genres;
    }

    public List<Genre> getAll() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (ResultSet resultSet = query("SELECT * FROM [Genre]")) {
            while (resultSet.next()) {
                genres.add(new Genre(resultSet));
            }
        }
        return genres;
    }
}
