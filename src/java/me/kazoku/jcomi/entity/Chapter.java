package me.kazoku.jcomi.entity;

import com.google.gson.JsonObject;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Chapter implements DataTransferObject {

    private final int id;
    private int chapterID;
    private int ordinal;
    private String imageUrl = "";

    public Chapter(int id) {
        this.id = id;

    }

    public Chapter(ResultSet resultSet) throws SQLException {
        this(resultSet.getInt("id"));
        this.chapterID = resultSet.getInt("chapter_id");
        this.ordinal = resultSet.getInt("ordinal");
        this.imageUrl = resultSet.getString("image_url");
    }

    public static Chapter fromJson(JsonObject json) {
        Chapter chapter = new Chapter(json.get("id").getAsInt());
        chapter.chapterID = json.get("chapter").getAsInt();
        chapter.ordinal = json.get("ordinal").getAsInt();
        chapter.imageUrl = json.get("image_url").getAsString();
        return chapter;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("chapterId", chapterID);
        json.addProperty("ordinal", ordinal);
        json.addProperty("imageUrl", imageUrl);
        return json;
    }

}
