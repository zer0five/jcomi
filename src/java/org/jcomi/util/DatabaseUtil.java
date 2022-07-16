package org.jcomi.util;

import me.kazoku.core.database.sql.SQLDriver;
import me.kazoku.core.database.sql.SQLSettings;
import me.kazoku.core.database.sql.client.JavaSQLClient;
import me.kazoku.core.database.sql.driver.MicrosoftSQLDriver;

import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {

    private DatabaseUtil() {
        // Utility class
    }

    public static Properties getDefaultConfig() {
        Properties properties = new Properties();
        properties.setProperty("database.host", "127.0.0.1");
        properties.setProperty("database.port", "1433");
        properties.setProperty("database.name", "jcomi");
        properties.setProperty("database.user", "sa");
        properties.setProperty("database.password", "");
        properties.setProperty("database.integratedSecurity", "false");
        properties.setProperty("database.development", "true");
        return properties;
    }

    public static Properties getConfig() {
        Properties config = getDefaultConfig();
        try {
            config.load(DatabaseUtil.class.getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, "Could not load config", e);
        }
        return config;
    }

    public static JavaSQLClient createClient() {
        Properties config = getConfig();
        boolean integratedSecurity = Optional.ofNullable(config.getProperty("database.integratedSecurity"))
            .map(Boolean::parseBoolean).orElse(false);
        boolean development = Optional.ofNullable(config.getProperty("database.development"))
            .map(Boolean::parseBoolean).orElse(true);
        SQLDriver driver = new MicrosoftSQLDriver(integratedSecurity, development);
        SQLSettings settings = driver.getDefaultSettings();
        Optional.ofNullable(config.getProperty("database.host"))
            .ifPresent(settings::setHost);
        Optional.ofNullable(config.getProperty("database.port"))
            .ifPresent(settings::setPort);
        Optional.ofNullable(config.getProperty("database.username"))
            .ifPresent(settings::setUsername);
        Optional.ofNullable(config.getProperty("database.password"))
            .ifPresent(settings::setPassword);
        Optional.ofNullable(config.getProperty("database.name"))
            .ifPresent(settings::setDatabase);
        return new JavaSQLClient(settings, driver);
    }

}
