package org.jcomi.controller.authentication;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.account.AccountDataAccess;
import org.jcomi.util.MD5Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Register", value = "/authentication/register")
public class Register extends ControllerServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        session = request.getSession(true);

        Optional<String> username = Optional.ofNullable(request.getParameter("username")).filter(s -> !s.isEmpty());
        Optional<String> email = Optional.ofNullable(request.getParameter("email")).filter(s -> !s.isEmpty());
        Optional<String> password = Optional.ofNullable(request.getParameter("password")).filter(s -> !s.isEmpty());
        Optional<String> displayName = Optional.ofNullable(request.getParameter("display-name")).filter(s -> !s.isEmpty());
        if (!(username.isPresent() && email.isPresent() && password.isPresent())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Optional<Account> accountByName = AccountDataAccess.getInstance().getOne(username.get());
            Optional<Account> accountByEmail = AccountDataAccess.getInstance().getOne(email.get());
            if (accountByName.isPresent() || accountByEmail.isPresent()) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }

            String hash = MD5Util.md5(password.get());
            Account newAccount = new Account();
            newAccount.setUsername(username.get());
            newAccount.setDisplayName(username.get()); // fallback to username if display name is not provided
            displayName.ifPresent(newAccount::setDisplayName);
            newAccount.setEmail(email.get());
            newAccount.setPassword(hash);
            AccountDataAccess.getInstance().insertAndGetIdentifier(newAccount);
            session.setAttribute("user", newAccount);
            Logger.getLogger(Register.class.getName()).log(Level.INFO, "Registered account: " + newAccount.getUsername() + " [" + newAccount.getId() + "]");
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (SQLException e) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, "Error while registering account", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
