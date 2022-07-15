package me.kazoku.jcomi.entity;

import com.google.gson.JsonObject;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Comic implements DataTransferObject {

    private final int id;
    private String name = "";
    private String altName = "";
    private String author = "";
    private String cover = "";
    private int views = 0;
    private String description = "";

    public Comic(int id) {
        this.id = id;
    }

    public Comic(ResultSet resultSet) throws SQLException {
        this(resultSet.getInt("id"));
        this.name = resultSet.getString("name");
        this.altName = resultSet.getString("alt_name");
        this.author = resultSet.getString("author");
        this.cover = resultSet.getString("cover");
        this.views = resultSet.getInt("views");
        this.description = resultSet.getString("description");

    }

    public static Comic fromJson(JsonObject json) {
        Comic comic = new Comic(json.get("id").getAsInt());
        comic.name = json.get("name").getAsString();
        comic.altName = json.get("altName").getAsString();
        comic.author = json.get("author").getAsString();
        comic.cover = json.get("cover").getAsString();
        comic.views = json.get("views").getAsInt();
        comic.description = json.get("description").getAsString();

        return comic;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("name", name);
        json.addProperty("altName", altName);
        json.addProperty("author", author);
        json.addProperty("cover", cover);
        json.addProperty("views", views);
        json.addProperty("description", description);
        return json;
    }

}
