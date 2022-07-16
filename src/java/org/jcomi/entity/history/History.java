package org.jcomi.entity.history;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class History implements DataTransferObject {
    private int id;
    private int accountId;
    private int chapterId;
    private long readDate;

    private int id, accountId, chapterId;
    private long readDate;
    
    public History() {
        this.id = 0;
        this.accountId = 0;
        this.chapterId = 0;
        this.readDate = System.currentTimeMillis();
    }

    public History(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.accountId = resultSet.getInt("Account_ID");
        this.chapterId = resultSet.getInt("Chapter_ID");
        this.readDate = resultSet.getLong("Read_Date");
    }
}
