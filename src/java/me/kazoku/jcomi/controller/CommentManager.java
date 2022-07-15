package me.kazoku.jcomi.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.kazoku.core.database.sql.client.JavaSQLClient;
import me.kazoku.jcomi.entity.Comment;
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
import me.kazoku.jcomi.entity.Comments;

@WebServlet(name = "CommentManager", value = "/manage/comment")
public class CommentManager extends HttpServlet {

    private static JavaSQLClient client;
    private static Comments comments; 

    @Override
    public void init() {
        client = DatabaseUtil.createClient();
        comments = new Comments(client);
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
                    Optional<Comment> comment = comments.getOne(id.get());
                    if (comment.isPresent()) {
                        json.addProperty("status", "success");
                        json.add("comment", comment.get().toJson());
                    } else {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Comment not found");
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "success");
                try {
                    JsonArray array = new JsonArray();
                    List<Comment> commentsList = comments.get(null);
                    if (!commentsList.isEmpty()) {
                        for (Comment comment : commentsList) {
                            array.add(comment.toJson());
                        }
                        json.add("comments", array);
                    } else {
                        json.addProperty("message", "No comments found");
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
                    List<Comment> prepareToDie = comments.get(id.get());
                    switch (prepareToDie.size()) {
                        case 0:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Comment not found");
                            break;
                        case 1:
                            comments.delete(prepareToDie.get(0));
                            json.addProperty("status", "success");
                            json.addProperty("message", "Comment deleted");
                            break;
                        default:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Multiple comments found");
                            break;
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "error");
                json.addProperty("message", "No comment id provided");
            }
            writer.write(json.toString());
        }
    }
}
