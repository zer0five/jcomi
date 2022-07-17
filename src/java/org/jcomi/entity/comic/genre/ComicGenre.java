package org.jcomi.entity.comic.genre;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.genre.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ComicGenre implements DataTransferObject {

    private int id;
    private int comicId;
    private int genreId;

    public ComicGenre() {
        this.id = 0;
        this.comicId = 0;
        this.genreId = 0;
    }

    public ComicGenre(Comic comic, Genre genre) {
        this.id = 0;
        this.comicId = comic.getId();
        this.genreId = genre.getId();
    }

    public ComicGenre(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("ID");
        comicId = resultSet.getInt("Comic_ID");
        genreId = resultSet.getInt("Genre_ID");
    }
}
