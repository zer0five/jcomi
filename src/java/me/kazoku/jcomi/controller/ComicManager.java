package me.kazoku.jcomi.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.kazoku.core.database.sql.client.JavaSQLClient;
import me.kazoku.jcomi.entity.Comic;
import me.kazoku.jcomi.entity.Comics;
import me.kazoku.jcomi.util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import me.kazoku.jcomi.entity.Comics;

@WebServlet(name = "ComicManager", value = "/manage/comic")
public class ComicManager extends HttpServlet {

    private static JavaSQLClient client;
    private static Comics comics;

    @Override
    public void init() {
        client = DatabaseUtil.createClient();
        comics = new Comics(client);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (request.getParameter("action") != null) {
            switch (request.getParameter("action").toLowerCase()) {
                case "delete":
                    doDelete(request, response);
                    break;
            }
        }
        try (PrintWriter writer = response.getWriter()) {
            JsonObject json = new JsonObject();
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (id.isPresent()) {
                try {
                    Optional<Comic> comic = comics.getOne(id.get());
                    if (comic.isPresent()) {
                        json.addProperty("status", "success");
                        json.add("comic", comic.get().toJson());
                    } else {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Comic not found");
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "success");
                try {
                    JsonArray array = new JsonArray();
                    List<Comic> comicsList = comics.get(null);
                    if (!comicsList.isEmpty()) {
                        for (Comic comic : comicsList) {
                            array.add(comic.toJson());
                        }
                        json.add("comics", array);
                    } else {
                        json.addProperty("message", "No comics found");
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            }
            writer.write(json.toString());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            JsonObject json = new JsonObject();
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (id.isPresent()) {
                try {
                    List<Comic> prepareToDie = comics.get(id.get());
                    switch (prepareToDie.size()) {
                        case 0:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Comic not found");
                            break;
                        case 1:
                            comics.delete(prepareToDie.get(0));
                            json.addProperty("status", "success");
                            json.addProperty("message", "Comic deleted");
                            break;
                        default:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Multiple comics found");
                            break;
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "error");
                json.addProperty("message", "No comic id provided");
            }
            writer.write(json.toString());
        }
    }
}
