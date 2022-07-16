package me.kazoku.core.database.sql.client;

import me.kazoku.core.database.sql.SQLDriver;
import me.kazoku.core.database.sql.SQLSettings;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            Driver driver = this.driver.getDriverClass().getConstructor().newInstance();
            return driver.connect(this.driver.createUrl(settings), properties);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(JavaSQLClient.class.getName()).log(Level.WARNING, "Cannot get connection from driver class", ex);
        }
        return DriverManager.getConnection(this.driver.createUrl(settings), properties);
    }
}
