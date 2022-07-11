package me.kazoku.core.database.sql.client;

import me.kazoku.core.database.sql.SQLDriver;
import me.kazoku.core.database.sql.SQLSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JavaSQLClient extends BaseSQLClient<Properties> {

    private final Properties properties;
    private final SQLDriver driver;

    public JavaSQLClient(SQLSettings settings, SQLDriver driver) {
        super(settings);
        this.properties = new Properties(settings.getClientProperties());
        this.properties.setProperty("user", settings.getUsername());
        this.properties.setProperty("password", settings.getPassword());
        this.driver = driver;
    }

    @Override
    public Properties getOriginal() {
        return this.properties;
    }

    @Override
    public SQLDriver getDriver() {
        return this.driver;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.driver.createUrl(settings), properties);
    }
}
