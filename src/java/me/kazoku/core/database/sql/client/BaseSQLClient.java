package me.kazoku.core.database.sql.client;

import me.kazoku.core.database.sql.SQLClient;
import me.kazoku.core.database.sql.SQLSettings;

public abstract class BaseSQLClient<T> implements SQLClient<T> {

    protected final SQLSettings settings;

    protected BaseSQLClient(SQLSettings settings) {
        this.settings = settings;
    }

    @Override
    public SQLSettings getSettings() {
        return this.settings;
    }

}
