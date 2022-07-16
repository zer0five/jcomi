package org.jcomi.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataTransferObject extends Serializable {
    void sync(ResultSet resultSet) throws SQLException;

}
