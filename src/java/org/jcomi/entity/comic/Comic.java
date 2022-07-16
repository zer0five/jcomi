package org.jcomi.entity.comic;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Comic implements DataTransferObject {
    private int id;
    private String name;
    private String altName;
    private String author;
    private String cover;
    private int views;
    private String description;

    private int uploaderId;

    public Comic() {
        this.id = 0;
        this.name = "";
        this.altName = "";
        this.author = "";
        this.cover = "https://via.placeholder.com/190x247?text=Cover";
        this.views = 0;
        this.description = "";
        this.uploaderId = 0;
    }

    public Comic(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.altName = resultSet.getString("alt_name");
        this.author = resultSet.getString("author");
        this.cover = resultSet.getString("cover");
        this.views = resultSet.getInt("views");
        this.description = resultSet.getString("description");
        this.uploaderId = resultSet.getInt("uploader_id");
    }

}
