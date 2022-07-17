package org.jcomi.entity.comic.chapter;

import org.jcomi.entity.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChapterDataAccess extends DataAccessObject<Chapter> {
    public ChapterDataAccess() {
        super();
    }

    @Override
    protected Chapter createObject(ResultSet resultSet) throws SQLException {
        return new Chapter(resultSet);
    }

    @Override
    public int insert(Chapter object) throws SQLException {
        return sqlUpdate(
            "INSERT INTO [Chapter] " +
                "([Comic_ID], [Title], [Ordinal]) " +
                "VALUES (?, ?, ?)",
            object.getComicId(),
            object.getTitle(),
            object.getOrdinal()
        );
    }

    @Override
    public Object insertAndGetIdentifier(Chapter object) throws SQLException {
        try (ResultSet resultSet = sqlQuery(
            "INSERT INTO [Chapter] " +
                "([Comic_ID], [Title], [Ordinal]) " +
                "OUTPUT INSERTED.ID " +
                "VALUES (?, ?, ?)",
            object.getComicId(),
            object.getTitle(),
            object.getOrdinal()
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
    public int update(Chapter object) throws SQLException {
        return sqlUpdate(
            "UPDATE [Chapter] " +
                "SET [Comic_ID] = ?, [Title] = ?, [Ordinal] = ? " +
                "WHERE [ID] = ?",
            object.getComicId(),
            object.getTitle(),
            object.getOrdinal(),
            object.getId()
        );
    }

    @Override
    public int delete(Chapter object) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [Chapter] WHERE [ID] = ?",
            object.getId()
        );
    }

    @Override
    public int delete(Object identifier) throws SQLException {
        return sqlUpdate(
            "DELETE FROM [Chapter] WHERE [ID] = ?",
            identifier
        );
    }

    @Override
    protected ResultSet selectById(Object identifier) throws SQLException {
        return sqlQuery(
            "SELECT [ID], [Comic_ID], [Title], [Ordinal] " +
                "FROM [Chapter] " +
                "WHERE [ID] = ?",
            identifier
        );
    }

    protected ResultSet selectByComicId(int comicId) throws SQLException {
        return sqlQuery(
            "SELECT [ID], [Comic_ID], [Title], [Ordinal] " +
                "FROM [Chapter] " +
                "WHERE [Comic_ID] = ?" +
                "ORDER BY [Ordinal]",
            comicId
        );
    }

    public List<Chapter> getChapters(int comicId) throws SQLException {
        List<Chapter> chapters = new ArrayList<>();
        try (ResultSet resultSet = sqlQuery("SELECT * FROM [Chapter] WHERE [Comic_ID] = ? ORDER BY [Ordinal]", comicId)) {
            while (resultSet.next()) {
                Chapter chapter = createObject(resultSet);
                chapter.setComicId(comicId);
                chapters.add(chapter);
            }
        }
        return chapters;
    }

    public List<Page> getPages(int chapterId) throws SQLException {
        List<Page> pages = new ArrayList<>();
        try (ResultSet resultSet = sqlQuery(
            "SELECT [ID], [Chapter_ID], [Ordinal], [Image_URL] " +
                "FROM [Page] " +
                "WHERE [Chapter_ID] = ? " +
                "ORDER BY [Ordinal]",
            chapterId
        )) {
            while (resultSet.next()) {
                pages.add(new Page(resultSet));
            }
        }
        return pages;
    }

    public int addPages(int chapterId, List<Page> pages) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<String> values = new ArrayList<>();
        sql.append("INSERT INTO [Page] ");
        sql.append("([Chapter_ID], [Ordinal], [Image_URL]) ");
        sql.append("VALUES ");
        for (Page page : pages) {
            sql.append("(?, ?, ?), ");
            values.add(String.valueOf(chapterId));
            values.add(String.valueOf(page.getOrdinal()));
            values.add(page.getImageUrl());
        }
        sql.delete(sql.length() - 2, sql.length());
        return sqlUpdate(sql.toString(), values.toArray());
    }


}
