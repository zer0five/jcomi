package org.jcomi.entity.comic.chapter;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
public class Chapter implements DataTransferObject {
    private int id;
    private int comicId;
    private int chapterId;
    private String title;
    private int ordinal;
    private List<String> pages;

    public Chapter() {
        this.id = 0;
        this.comicId = 0;
        this.chapterId = 0;
        this.title = "";
        this.ordinal = 0;
    }

    public Chapter(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.comicId = resultSet.getInt("Comic_ID");
        this.chapterId = resultSet.getInt("Chapter_ID");
        this.title = resultSet.getString("Title");
        this.ordinal = resultSet.getInt("Ordinal");
    }
}
