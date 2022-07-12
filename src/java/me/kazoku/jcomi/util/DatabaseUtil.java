package me.kazoku.jcomi.util;

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

    public static JavaSQLClient createClient() {
        Properties config = new Properties(System.getProperties());
        try {
            config.load(DatabaseUtil.class.getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, "Could not load config", e);
        }
        boolean integratedSecurity = Optional.ofNullable(config.getProperty("database.integratedSecurity"))
                .or(() -> Optional.ofNullable(System.getenv("MSSQL_INTEGRATED_SECURITY")))
                .map(Boolean::parseBoolean).orElse(false);
        boolean development = Optional.ofNullable(config.getProperty("database.development"))
                .or(() -> Optional.ofNullable(System.getenv("MSSQL_DEVELOPMENT")))
                .map(Boolean::parseBoolean).orElse(true);
        SQLDriver driver = new MicrosoftSQLDriver(integratedSecurity, development);
        SQLSettings settings = driver.getDefaultSettings();
        Optional.ofNullable(config.getProperty("database.host"))
                .filter(s -> !s.isEmpty())
                .or(() -> Optional.ofNullable(System.getenv("DATABASE_HOST")))
                .ifPresent(settings::setHost);
        Optional.ofNullable(config.getProperty("database.port"))
                .filter(s -> !s.isEmpty())
                .or(() -> Optional.ofNullable(System.getenv("DATABASE_PORT")))
                .ifPresent(settings::setPort);
        Optional.ofNullable(config.getProperty("database.username"))
                .filter(s -> !s.isEmpty())
                .or(() -> Optional.ofNullable(System.getenv("DATABASE_USERNAME")))
                .ifPresent(settings::setUsername);
        Optional.ofNullable(config.getProperty("database.password"))
                .filter(s -> !s.isEmpty())
                .or(() -> Optional.ofNullable(System.getenv("DATABASE_PASSWORD")))
                .ifPresent(settings::setPassword);
        Optional.ofNullable(config.getProperty("database.name"))
                .filter(s -> !s.isEmpty())
                .or(() -> Optional.ofNullable(System.getenv("DATABASE_NAME")))
                .ifPresent(settings::setDatabase);
        return new JavaSQLClient(settings, driver);
    }

}
