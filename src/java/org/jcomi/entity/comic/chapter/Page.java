package org.jcomi.entity.comic.chapter;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Page implements DataTransferObject {

    private int id;
    private int chapterId;
    private int ordinal;
    private String imageUrl;

    public Page() {
        this.id = 0;
        this.chapterId = 0;
        this.ordinal = 0;
        this.imageUrl = "";
    }

    public Page(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.chapterId = resultSet.getInt("Chapter_ID");
        this.ordinal = resultSet.getInt("Ordinal");
        this.imageUrl = resultSet.getString("Image_URL").replace("\\", File.separator);
    }
}
