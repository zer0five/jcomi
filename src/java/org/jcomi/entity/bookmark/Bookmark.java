package org.jcomi.entity.bookmark;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Bookmark implements DataTransferObject {
    private int id;
    private int accountId;
    private int comicId;

    public Bookmark() {
        this.id = 0;
        this.accountId = 0;
        this.comicId = 0;
    }

    public Bookmark(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.id = resultSet.getInt("Account_ID");
        this.id = resultSet.getInt("Comic_ID");

    }
}
