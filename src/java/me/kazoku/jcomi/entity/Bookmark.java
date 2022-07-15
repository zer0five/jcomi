
import com.google.gson.JsonObject;
import lombok.Data;
import me.kazoku.jcomi.entity.DataTransferObject;
import java.sql.ResultSet;
import java.sql.SQLException;
@Data
public class Bookmark implements DataTransferObject {
    private final int id;
    private int ComicId;
    private int AccountId;
  

    public Bookmark(int id) {
        this.id = id;
    }

    public Bookmark(java.sql.ResultSet resultSet) throws SQLException {
        this(resultSet.getInt("id"));
        this.ComicId = resultSet.getInt("Comic_ID");
        this.AccountId = resultSet.getInt("Account_ID");
    }
    public static Bookmark fromJson(JsonObject json) {
        Bookmark bookmark = new Bookmark(json.get("id").getAsInt());
        bookmark.ComicId = json.get("ComicId").getAsInt();
        bookmark.AccountId = json.get("AccountId").getAsInt();
        return bookmark;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("ComicId", ComicId);
        json.addProperty("AccoutId", AccountId);
        return json;
    }    

}