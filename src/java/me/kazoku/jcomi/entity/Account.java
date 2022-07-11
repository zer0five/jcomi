package me.kazoku.jcomi.entity;

import com.google.gson.JsonObject;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Account implements DataTransferObject {

    private final int id;
    private final String username;
    private final String email;
    private String password = "";
    private String displayName = "";
    private String avatar = "";
    private boolean hidden = false;
    private boolean isAdmin = false;
    private boolean isUploader = false;

    public Account(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Account(ResultSet resultSet) throws SQLException {
        this(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"));
        this.password = resultSet.getString("password");
        this.displayName = resultSet.getString("display_name");
        this.avatar = resultSet.getString("avatar");
        this.hidden = resultSet.getBoolean("hidden");
        this.isAdmin = resultSet.getBoolean("is_admin");
        this.isUploader = resultSet.getBoolean("is_uploader");
    }

    public static Account fromJson(JsonObject json) {
        Account account = new Account(json.get("id").getAsInt(), json.get("username").getAsString(), json.get("email").getAsString());
        account.password = json.get("password").getAsString();
        account.displayName = json.get("displayName").getAsString();
        account.avatar = json.get("avatar").getAsString();
        account.hidden = json.get("hidden").getAsBoolean();
        account.isAdmin = json.get("isAdmin").getAsBoolean();
        account.isUploader = json.get("isUploader").getAsBoolean();
        return account;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("username", username);
        json.addProperty("email", email);
        json.addProperty("displayName", displayName);
        json.addProperty("avatar", avatar);
        json.addProperty("hidden", hidden);
        json.addProperty("isAdmin", isAdmin);
        json.addProperty("isUploader", isUploader);
        return json;
    }

}
