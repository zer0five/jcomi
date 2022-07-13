package me.kazoku.jcomi.controller.authentication;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.kazoku.core.database.sql.client.JavaSQLClient;
import me.kazoku.jcomi.entity.Account;
import me.kazoku.jcomi.entity.Accounts;
import me.kazoku.jcomi.util.DatabaseUtil;
import me.kazoku.jcomi.util.MD5;

@WebServlet(name = "Login", value = "/authentication/login")
public class Login extends HttpServlet {

    private static JavaSQLClient client;
    private static Accounts accounts;

    @Override
    public void init() {
        client = DatabaseUtil.createClient();
        accounts = new Accounts(client);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonObject json = new JsonObject();
        Optional<String> username = Optional.ofNullable(request.getParameter("username"));
        Optional<String> password = Optional.ofNullable(request.getParameter("password"));
        if (username.isPresent() && password.isPresent()) {
            try {
                Optional<Account> account = accounts.getOne(username.get());
                if (account.isPresent()) {
                    String hash = MD5.getMd5(password.get());
                    if (hash.equals(account.get().getPassword())) {
                        json.addProperty("status", "success");
                        json.addProperty("message", "Login succesfully");
                        request.getSession().setAttribute("user", account.get());
                    } else {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Wrong password");
                    }
                } else {
                    json.addProperty("status", "error");
                    json.addProperty("message", "Account not found");
                }

            } catch (SQLException e) {
                json.addProperty("status", "error");
                json.addProperty("message", e.getMessage());
            }
        } else {
            json.addProperty("status", "error");
            json.addProperty("message", "Missing username and/or password");
        }
        try (PrintWriter writer = response.getWriter()) {
            writer.write(json.toString());
        }
    }

    @Override
    public void destroy() {

    }
}
