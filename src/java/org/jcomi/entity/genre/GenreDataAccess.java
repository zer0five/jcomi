package org.jcomi.entity.genre;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDataAccess extends DataAccessObject<Genre> {
    public GenreDataAccess() {
        super();
    }
    @Override
    protected Genre createObject(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet);
    }

    @Override
    public int insert(Genre object) throws SQLException {
        return sqlUpdate(
            "INSERT INTO [Genre] " +
                "([Genre], [Description]) " +
                "VALUES (?, ?)",
            object.getId(),
            object.getDescription()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Genre object) throws SQLException {
        try (ResultSet resultSet = sqlQuery(
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
        return sqlUpdate(
            "UPDATE [Genre] " +
                "SET [Genre] = ?, [Description] = ? " +
                "WHERE [ID] = ?",
            object.getId(),
            object.getDescription()
        );
    }

    @Override
    public int delete(Genre object) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [Genre] " +
                "WHERE [ID] = ?",
            object.getId()
        );
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [Genre] " +
                "WHERE [ID] = ?",
            identifier
        );
    }

    @Override
    protected ResultSet selectById(Object identifier) throws SQLException {
        return sqlQuery(
            "SELECT * FROM [Genre] " +
                "WHERE [ID] = ?",
            identifier
        );
    }

    @Override
    public List<Genre> get(Object identifier) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (ResultSet resultSet = selectById(identifier)) {
            while (resultSet.next()) {
                genres.add(new Genre(resultSet));
            }
        }
        return genres;
    }

    public List<Genre> getAll() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (ResultSet resultSet = sqlQuery("SELECT [ID], [Genre], [Description] FROM [Genre] ORDER BY [Genre]", new Object[0])) {
            while (resultSet.next()) {
                genres.add(new Genre(resultSet));
            }
        }
        return genres;
    }
}
