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

@WebServlet(name = "Register", value = "/authentication/register")
public class Register extends HttpServlet {

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
        Optional<String> email = Optional.ofNullable(request.getParameter("email"));
        Optional<String> password = Optional.ofNullable(request.getParameter("password"));
        if (username.isPresent() && email.isPresent() && password.isPresent()) {
            try {
                Optional<Account> account = accounts.getOne(username.get());
                if (account.isPresent()) {
                    json.addProperty("status", "error");
                    json.addProperty("message", "Account existed");
                } else {
                    account = accounts.getOne(email.get());
                    if (account.isPresent()) {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Account existed");
                    } else {
                        String hash = MD5.getMd5(password.get());
                        Account newAccount = new Account(0, username.get(),email.get());
                        newAccount.setPassword(hash);
                        Optional.ofNullable(request.getParameter("display-name")).ifPresent(newAccount::setDisplayName);      
                        accounts.insert(newAccount);
                        json.addProperty("status", "success");
                        json.addProperty("message", "Register succesfully");
                    }
                }

            } catch (SQLException e) {
                json.addProperty("status", "error");
                json.addProperty("message", e.getMessage());
            }
        } else {
            json.addProperty("status", "error");
            json.addProperty("message", "Missing info");
        }
        try (PrintWriter writer = response.getWriter()) {
            writer.write(json.toString());
        }
    }

    @Override
    public void destroy() {

    }
}
