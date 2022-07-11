package me.kazoku.core.database.sql.driver;

import me.kazoku.core.database.sql.SQLDriver;
import me.kazoku.core.database.sql.SQLSettings;

import java.sql.Driver;

public class MicrosoftSQLDriver implements SQLDriver {

    private final boolean integratedSecurity;
    private final boolean development;

    public MicrosoftSQLDriver(boolean integratedSecurity, boolean development) {
        this.integratedSecurity = integratedSecurity;
        this.development = development;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Driver> getDriverClass() {
        try {
            return com.microsoft.sqlserver.jdbc.SQLServerDriver.class;
        } catch (NoClassDefFoundError | ExceptionInInitializerError e) {
            try {
                return (Class<? extends Driver>) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException("Could not find the driver class.", ex);
            }
        }
    }

    @Override
    public String createUrl(SQLSettings settings) {
        SQLSettings newSettings = new SQLSettings(settings);
        if (integratedSecurity) {
            newSettings.setClientProperty("integratedSecurity", "true");
        } else {
            newSettings.setClientProperty("integratedSecurity", "false");
            newSettings.setClientProperty("user", settings.getUsername());
            newSettings.setClientProperty("password", settings.getPassword());
        }
        if (development) {
            newSettings.setClientProperty("encrypt", "true");
            newSettings.setDriverProperty("trustServerCertificate", "true");
        }
        return SQLDriver.super.createUrl(newSettings);
    }

    @Override
    public SQLSettings getDefaultSettings() {
        SQLSettings settings = new SQLSettings();
        settings.setHost("127.0.0.1");
        settings.setPort("1433");
        settings.setDatabase("master");
        settings.setUsername("sa");
        settings.setPassword("");
        return settings;
    }

    @Override
    public String getJDBCDriverName() {
        return "sqlserver";
    }

}
