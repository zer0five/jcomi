package me.kazoku.core.database.sql;

import lombok.Getter;

import java.util.Optional;
import java.util.Properties;

/**
 * @author Kazoku
 * @version 1.0
 * <p>
 * This class is used to define the settings that will
 * be used to connect to the database.
 * @since 2022-07-01
 */
@Getter
public class SQLSettings {
    private final Properties clientProperties;
    private final Properties driverProperties;
    private String host;
    private String port;
    private String username;
    private String password;
    private Optional<String> database;

    public SQLSettings(SQLSettings settings) {
        this.clientProperties = new Properties(settings.getClientProperties());
        this.driverProperties = new Properties(settings.getDriverProperties());
        this.host = settings.getHost();
        this.port = settings.getPort();
        this.username = settings.getUsername();
        this.password = settings.getPassword();
        this.database = settings.getDatabase();
    }

    public SQLSettings() {
        this.clientProperties = new Properties();
        this.driverProperties = new Properties();
        this.host = "127.0.0.1";
        this.port = "3306";
        this.username = "root";
        this.password = "";
        this.database = Optional.empty();
    }

    public Properties getClientProperties() {
        return new Properties(this.clientProperties);
    }

    public Properties getDriverProperties() {
        return new Properties(this.driverProperties);
    }

    public SQLSettings setClientProperty(String key, String value) {
        this.clientProperties.setProperty(key, value);
        return this;
    }

    public SQLSettings setDriverProperty(String key, String value) {
        this.driverProperties.setProperty(key, value);
        return this;
    }

    public SQLSettings setHost(String host) {
        this.host = host;
        return this;
    }

    public SQLSettings setPort(String port) {
        this.port = port;
        return this;
    }

    public SQLSettings setUsername(String username) {
        this.username = username;
        return this;
    }

    public SQLSettings setPassword(String password) {
        this.password = password;
        return this;
    }

    public SQLSettings setDatabase(String database) {
        this.database = Optional.ofNullable(database);
        return this;
    }

    public String getDriverPropertiesString(String prefix, String delimiter) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (String key : this.driverProperties.stringPropertyNames()) {
            sb.append(key);
            sb.append('=');
            sb.append(this.driverProperties.getProperty(key));
            sb.append(delimiter);
        }
        sb.delete(sb.length() - delimiter.length(), sb.length());
        return sb.toString();
    }
}
