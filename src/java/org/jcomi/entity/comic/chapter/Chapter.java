package org.jcomi.entity.comic.chapter;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class Chapter implements DataTransferObject {
    private int id;
    private int comicId;
    private String title;
    private int ordinal;
    private Date uploadDate;

    public Chapter() {
        this.id = 0;
        this.comicId = 0;
        this.title = "";
        this.ordinal = 0;
        this.uploadDate = new Date();
    }

    public Chapter(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.comicId = resultSet.getInt("Comic_ID");
        this.title = resultSet.getString("Title");
        this.ordinal = resultSet.getInt("Ordinal");
        this.uploadDate = resultSet.getDate("Upload_Date");
    }
}
