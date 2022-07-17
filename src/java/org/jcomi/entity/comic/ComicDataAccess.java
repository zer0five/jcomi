package org.jcomi.entity.comic;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ComicDataAccess extends DataAccessObject<Comic> {

    public ComicDataAccess() {
        super();
    }

    @Override
    protected Comic createObject(ResultSet resultSet) throws SQLException {
        return new Comic(resultSet);
    }

    @Override
    public int insert(Comic object) throws SQLException {
        return sqlUpdate(
                "INSERT INTO [Comic] "
                + "([Name], [Alt_Name], [Author], [Cover], [Description], [Views], [Uploader_ID]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                object.getName(),
                object.getAltName(),
                object.getAuthor(),
                object.getCover(),
                object.getDescription(),
                object.getViews(),
                object.getUploaderId()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Comic object) throws SQLException {
        try ( ResultSet resultSet = sqlQuery(
                "INSERT INTO [Comic] "
                + "([Name], [Alt_Name], [Author], [Cover], [Description], [Views], [Uploader_ID]) "
                + "OUTPUT INSERTED.ID "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                object.getName(),
                object.getAltName(),
                object.getAuthor(),
                object.getCover(),
                object.getDescription(),
                object.getViews(),
                object.getUploaderId()
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
    public int update(Comic object) throws SQLException {
        int result = sqlUpdate(
                "UPDATE [Comic] SET [Name] = ?, [Alt_Name] = ?, [Author] = ?, [Cover] = ?, [Description] = ?, [Views] = ? WHERE [ID] = ?",
                object.getName(),
                object.getAltName(),
                object.getAuthor(),
                object.getCover(),
                object.getDescription(),
                object.getViews(),
                object.getId()
        );
        if (result != 0) {
            try ( ResultSet resultSet = selectById(object.getId())) {
                if (resultSet.next()) {
                    object.sync(resultSet);
                } else {
                    throw new SQLException("No result returned");
                }
            }
        }
        return result;
    }

    @Override
    public int delete(Comic object) throws SQLException {
        return delete(object.getId());
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        if (identifier instanceof Integer) {
            return sqlUpdate("DELETE FROM [Comic] WHERE [ID] = ?", identifier);
        } else {
            throw new IllegalArgumentException("Identifier must be an Integer");
        }
    }

    @Override
    protected ResultSet selectById(Object identifier) throws SQLException {
        return sqlQuery("SELECT * FROM [Comic] WHERE [ID] = ?", identifier);
    }

    @Override
    public List<Comic> get(Object identifier) throws SQLException {
        List<Comic> comics = new ArrayList<>();
        try ( ResultSet resultSet = sqlQuery("SELECT * FROM [Comic] WHERE [ID] = ? OR [Genre] = ?", identifier, identifier)) {
            while (resultSet.next()) {
                comics.add(createObject(resultSet));
            }
        }
        return comics;
    }

    public List<Comic> getAll() throws SQLException {
        List<Comic> comics = new ArrayList<>();
        try ( ResultSet resultSet = sqlQuery("SELECT * FROM [Comic]")) {
            while (resultSet.next()) {
                comics.add(createObject(resultSet));
            }
        }
        return comics;
    }

    @Override
    public Optional<Comic> getOne(Object identifier) throws SQLException {
        try ( ResultSet resultSet = selectById(identifier)) {
            if (resultSet.next()) {
                return Optional.of(new Comic(resultSet));
            }
        }
        return Optional.empty();
    }
}
