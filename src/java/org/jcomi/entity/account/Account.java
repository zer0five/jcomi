package org.jcomi.entity.account;

import lombok.Data;
import org.jcomi.entity.DataTransferObject;
import org.jcomi.util.MD5Util;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Account implements DataTransferObject {
    private int id;
    private String username;
    private String email;
    private String password;
    private String displayName;
    private boolean banned;
    private boolean isAdmin;
    private boolean isUploader;

    public Account() {
        this.id = 0;
        this.username = "";
        this.email = "";
        this.password = "";
        this.displayName = "";
        this.banned = false;
        this.isAdmin = false;
        this.isUploader = false;
    }

    public Account(ResultSet resultSet) throws SQLException {
        this();
        this.sync(resultSet);
    }

    @Override
    public void sync(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("ID");
        this.username = resultSet.getString("Username");
        this.email = resultSet.getString("Email");
        this.password = resultSet.getString("Password");
        this.displayName = resultSet.getString("Display_Name");
        this.banned = resultSet.getBoolean("Banned");
        this.isAdmin = resultSet.getBoolean("Is_Admin");
        this.isUploader = resultSet.getBoolean("Is_Uploader");
    }

    public String getAvatar() {
        return "https://www.gravatar.com/avatar/" + MD5Util.md5(email) + "?s=128";
    }

}
