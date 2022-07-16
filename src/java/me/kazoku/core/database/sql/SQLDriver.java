package me.kazoku.core.database.sql;

import java.sql.Driver;

/**
 * @author Kazoku
 * @version 1.0
 * <p>
 * This interface is used to define the SQL driver that will be used to connect
 * to the database.
 * @since 2022-07-01
 */
public interface SQLDriver {

    Class<? extends Driver> getDriverClass();

    default String createUrl(SQLSettings settings) {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:");
        builder.append(getJDBCDriverName());
        builder.append("://");
        builder.append(settings.getHost());
        builder.append(':');
        builder.append(settings.getPort());
        settings.getDatabase()
            .ifPresent(database -> settings.setDriverProperty("databaseName", database));
        builder.append(settings.getDriverPropertiesString(";", ";"));
        return builder.toString();
    }

    SQLSettings getDefaultSettings();

    String getJDBCDriverName();

}
