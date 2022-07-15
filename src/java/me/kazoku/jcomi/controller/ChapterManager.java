package me.kazoku.jcomi.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.kazoku.core.database.sql.client.JavaSQLClient;
import me.kazoku.jcomi.entity.Chapter;
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
import me.kazoku.jcomi.entity.Chapters;

@WebServlet(name = "ChapterManager", value = "/manage/chapter")
public class ChapterManager extends HttpServlet {

    private static JavaSQLClient client;
    private static Chapters chapters;

    @Override
    public void init() {
        client = DatabaseUtil.createClient();
        chapters = new Chapters(client);
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
        try ( PrintWriter writer = response.getWriter()) {
            JsonObject json = new JsonObject();
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (id.isPresent()) {
                try {
                    Optional<Chapter> chapter = chapters.getOne(id.get());
                    if (chapter.isPresent()) {
                        json.addProperty("status", "success");
                        json.add("chapter", chapter.get().toJson());
                    } else {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Chapter not found");
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "success");
                try {
                    JsonArray array = new JsonArray();
                    List<Chapter> chaptersList = chapters.get(null);
                    if (!chaptersList.isEmpty()) {
                        for (Chapter chapter : chaptersList) {
                            array.add(chapter.toJson());
                        }
                        json.add("chapters", array);
                    } else {
                        json.addProperty("message", "No chapters found");
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

        try ( PrintWriter writer = response.getWriter()) {
            JsonObject json = new JsonObject();
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (id.isPresent()) {
                try {
                    List<Chapter>prepareToDie = chapters.get(id.get());
                    switch (prepareToDie.size()) {
                        case 0:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Chapter not found");
                            break;
                        case 1:
                            chapters.delete(prepareToDie.get(0));
                            json.addProperty("status", "success");
                            json.addProperty("message", "Chapter deleted");
                            break;
                        default:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Multiple chapters found");
                            break;
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "error");
                json.addProperty("message", "No chapter id provided");
            }
            writer.write(json.toString());
        }
    }
}
