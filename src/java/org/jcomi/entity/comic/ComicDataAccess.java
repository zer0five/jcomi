package org.jcomi.entity.comic;

import me.kazoku.core.database.sql.client.JavaSQLClient;
import org.jcomi.entity.DataAccessObject;
import org.jcomi.util.DatabaseUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComicDataAccess extends DataAccessObject<Comic> {

    private static ComicDataAccess instance;

    private ComicDataAccess(JavaSQLClient client) {
        super(client);
    }

    public static ComicDataAccess getInstance() {
        if (instance == null) {
            instance = new ComicDataAccess(DatabaseUtil.createClient());
        }
        return instance;
    }

    @Override
    protected Comic createObject(ResultSet resultSet) throws SQLException {
        return new Comic(resultSet);
    }

    @Override
    public int insert(Comic object) throws SQLException {
        return update(
            "INSERT INTO [Comic] " +
                "([Name], [Alt_Name], [Author], [Cover], [Description], [Views]) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
            object.getName(),
            object.getAltName(),
            object.getAuthor(),
            object.getCover(),
            object.getDescription(),
            object.getViews()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Comic object) throws SQLException {
        try (ResultSet resultSet = query(
            "INSERT INTO [Comic] " +
                "([Name], [Alt_Name], [Author], [Cover], [Description], [Views]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?, ?, ?, ?, ?)",
            object.getName(),
            object.getAltName(),
            object.getAuthor(),
            object.getCover(),
            object.getDescription(),
            object.getViews()
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
        int result = update(
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
            object.sync(query(object.getId()));
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
            return update("DELETE FROM [Comic] WHERE [ID] = ?", identifier);
        } else {
            throw new IllegalArgumentException("Identifier must be an Integer");
        }
    }

    @Override
    protected ResultSet query(Object identifier) throws SQLException {
        return query("SELECT * FROM [Comic] WHERE [ID] = ?", identifier);
    }

    @Override
    public List<Comic> get(Object identifier) throws SQLException {
        List<Comic> comics = new ArrayList<>();
        try (ResultSet resultSet = query("SELECT * FROM [Comic] WHERE [Account_ID] = ?", identifier)) {
            while (resultSet.next()) {
                comics.add(new Comic(resultSet));
            }
        }
        return comics;
    }

    @Override
    public Optional<Comic> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = query(identifier)) {
            if (resultSet.next()) {
                return Optional.of(new Comic(resultSet));
            }
        }
        return Optional.empty();
    }
}
