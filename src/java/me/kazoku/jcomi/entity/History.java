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
public class History implements DataTransferObject {

    private final int id;
    private int ChapterId;
    private int AccountId;
    private long ReadDate;

    public History(int id) {
        this.id = id;
    }

    public History(ResultSet resultSet) throws SQLException {
        this(resultSet.getInt("id"));
        this.ChapterId = resultSet.getInt("Chapter_ID");
        this.AccountId = resultSet.getInt("Account_ID");
        this.ReadDate = resultSet.getLong("Read_Date");

    }

    public static History fromJson(JsonObject json) {
        History history = new History(json.get("id").getAsInt());
        history.ChapterId = json.get("ChapterId").getAsInt();
        history.AccountId = json.get("AccountId").getAsInt();
        history.ReadDate = json.get("ReadDate").getAsLong();
        return history;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("ChapterId", ChapterId);
        json.addProperty("AccoutId", AccountId);
        json.addProperty("ReadDate", ReadDate);
        return json;
    }

}
