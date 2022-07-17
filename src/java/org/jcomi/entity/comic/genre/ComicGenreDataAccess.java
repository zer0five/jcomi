package org.jcomi.entity.comic.genre;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComicGenreDataAccess extends DataAccessObject<ComicGenre> {

    @Override
    protected ComicGenre createObject(ResultSet resultSet) throws SQLException {
        return new ComicGenre(resultSet);
    }

    @Override
    public int insert(ComicGenre object) throws SQLException {
        return sqlUpdate(
            "INSERT INTO [Comic_Genre] " +
                "([Comic_ID], [Genre_ID]) " +
                "VALUES (?, ?)",
            object.getComicId(),
            object.getGenreId()
        );
    }

    @Override
    public Object insertAndGetIdentifier(ComicGenre object) throws SQLException {
        try (ResultSet resultSet = sqlQuery(
            "INSERT INTO [Comic_Genre] " +
                "([Comic_ID], [Genre_ID]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?)",
            object.getComicId(),
            object.getGenreId()
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
    public int update(ComicGenre object) throws SQLException {
        return sqlUpdate(
            "UPDATE [Comic_Genre] " +
                "SET [Comic_ID] = ?, [Genre_ID] = ? " +
                "WHERE [ID] = ?",
            object.getComicId(),
            object.getGenreId(),
            object.getId()
        );
    }

    @Override
    public int delete(ComicGenre object) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [Comic_Genre] WHERE [ID] = ?",
            object.getId()
        );
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [Comic_Genre] WHERE [ID] = ?",
            identifier
        );
    }

    @Override
    protected ResultSet selectById(Object identifier) throws SQLException {
        return sqlQuery(
            "SELECT [ID], [Comic_ID], [Genre_ID] " +
                "FROM [Comic_Genre] " +
                "WHERE [ID] = ?",
            identifier
        );
    }
}
