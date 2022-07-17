package org.jcomi.entity.bookmark;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookmarkDataAccess extends DataAccessObject<Bookmark> {

    public BookmarkDataAccess() {
        super();
    }

    @Override
    protected Bookmark createObject(ResultSet resultSet) throws SQLException {
        return new Bookmark(resultSet);
    }

    @Override
    public int insert(Bookmark object) throws SQLException {
        return sqlUpdate(
            "INSERT INTO [Bookmark] " +
                "([Account_ID], [Comic_ID]) " +
                "VALUES (?, ?)",
            object.getAccountId(),
            object.getComicId()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Bookmark object) throws SQLException {
        try (ResultSet resultSet = sqlQuery(
            "INSERT INTO [Comic] " +
                "([Account_ID], [Comic_ID]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?)",
            object.getAccountId(),
            object.getComicId()
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
    public int update(Bookmark object) throws SQLException {
        int result = sqlUpdate(
            "UPDATE [Bookmark] SET [Account_ID] = ?, [Comic_ID] = ? WHERE [ID] = ?",
            object.getAccountId(),
            object.getComicId(),
            object.getId()
        );
        if (result != 0) {
            object.sync(selectById(object.getId()));
        }
        return result;
    }

    @Override
    public int delete(Bookmark object) throws SQLException {
        return delete(object.getId());
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        if (identifier instanceof Integer) {
            return sqlUpdate("DELETE FROM [Bookmark] WHERE [ID] = ?", identifier);
        } else {
            throw new IllegalArgumentException("Identifier must be an Integer");
        }
    }

    @Override
    protected ResultSet selectById(Object identifier) throws SQLException {
        return sqlQuery("SELECT * FROM [Bookmark] WHERE [ID] = ?", identifier);
    }

    @Override
    public List<Bookmark> get(Object identifier) throws SQLException {
        List<Bookmark> bookmarks = new ArrayList<>();
        try (ResultSet resultSet = sqlQuery("SELECT * FROM [Bookmark] WHERE [Account_ID] = ?", identifier)) {
            while (!resultSet.isClosed() && resultSet.next()) {
                bookmarks.add(new Bookmark(resultSet));
            }
        }
        return bookmarks;
    }

    @Override
    public Optional<Bookmark> getOne(Object identifier) throws SQLException {
        try (ResultSet resultSet = selectById(identifier)) {
            if (!resultSet.isClosed() && resultSet.next()) {
                return Optional.of(new Bookmark(resultSet));
            }
        }
        return Optional.empty();
    }
}
