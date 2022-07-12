package me.kazoku.jcomi.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.kazoku.core.database.sql.client.JavaSQLClient;
import me.kazoku.jcomi.entity.Account;
import me.kazoku.jcomi.entity.Accounts;
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

@WebServlet(name = "AccountManager", value = "/manage/account")
public class AccountManager extends HttpServlet {

    private static JavaSQLClient client;
    private static Accounts accounts;

    @Override
    public void init() {
        client = DatabaseUtil.createClient();
        accounts = new Accounts(client);
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
                    Optional<Account> account = accounts.getOne(id.get());
                    if (account.isPresent()) {
                        json.addProperty("status", "success");
                        json.add("account", account.get().toJson());
                    } else {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Account not found");
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "success");
                try {
                    JsonArray array = new JsonArray();
                    List<Account> accountsList = accounts.get(null);
                    if (!accountsList.isEmpty()) {
                        for (Account account : accountsList) {
                            array.add(account.toJson());
                        }
                        json.add("accounts", array);
                    } else {
                        json.addProperty("message", "No accounts found");
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
                    List<Account> prepareToDie = accounts.get(id.get());
                    switch (prepareToDie.size()) {
                        case 0:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Account not found");
                            break;
                        case 1:
                            accounts.delete(prepareToDie.get(0));
                            json.addProperty("status", "success");
                            json.addProperty("message", "Account deleted");
                            break;
                        default:
                            json.addProperty("status", "error");
                            json.addProperty("message", "Multiple accounts found");
                            break;
                    }
                } catch (SQLException e) {
                    json.addProperty("status", "error");
                    json.addProperty("message", e.getMessage());
                }
            } else {
                json.addProperty("status", "error");
                json.addProperty("message", "No account id provided");
            }
            writer.write(json.toString());
        }
    }
}
