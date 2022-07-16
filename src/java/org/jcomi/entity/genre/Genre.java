package org.jcomi.entity.genre;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Genre implements DataTransferObject {
    private int id;
    private String genre;
    private String description;

    public Genre() {
        this.id = 0;
        this.genre = "";
        this.description = "";
    }

    public Genre(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.genre = resultSet.getString("Genre");
        this.description = resultSet.getString("Description");
    }

}
