package me.kazoku.jcomi.entity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DataAccessObject<T extends DataTransferObject> {

    int insert(T object) throws SQLException;

    int update(T object) throws SQLException;

    int delete(T object) throws SQLException;

    List<T> get(Object identifier) throws SQLException;

    Optional<T> getOne(Object identifier) throws SQLException;

}
