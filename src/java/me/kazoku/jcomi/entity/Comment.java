/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.kazoku.jcomi.entity;

import com.google.gson.JsonObject;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Comment implements DataTransferObject {

    private final int id;
    private int ComicId;
    private int AccountId;
    private String Content;
    private long PostedDate;

    public Comment(int id) {
        this.id = id;
    }

    public Comment(ResultSet resultSet) throws SQLException {
        this(resultSet.getInt("id"));
        this.ComicId = resultSet.getInt("Comic_ID");
        this.AccountId = resultSet.getInt("Account_ID");
        this.Content = resultSet.getString("Content");
        this.PostedDate = resultSet.getLong("Posted_Date");

    }
    public static Comment fromJson(JsonObject json) {
        Comment comment = new Comment(json.get("id").getAsInt());
        comment.ComicId = json.get("ComicId").getAsInt();
        comment.AccountId = json.get("AccountId").getAsInt();
        comment.Content = json.get("Content").getAsString();
        comment.PostedDate = json.get("PostedDate").getAsLong();
        return comment;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("ComicId", ComicId);
        json.addProperty("AccoutId", AccountId);
        json.addProperty("Content", Content);
        json.addProperty("PostedDate", PostedDate);
        return json;
    }    

}
