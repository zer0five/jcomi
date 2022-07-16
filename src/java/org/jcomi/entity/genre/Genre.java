package org.jcomi.entity.genre;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Genre implements DataTransferObject {
    private int id;
    private int comicId;
    private int genreId;


    public Genre() {
        this.id = 0;
        this.comicId = 0;
        this.genreId = 0;
    }

    public Genre(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.comicId = resultSet.getInt("Comic_ID");
        this.genreId = resultSet.getInt("Genre_ID");
    }

}
