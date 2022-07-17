package org.jcomi.controller.authentication;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.account.AccountDataAccess;
import org.jcomi.util.MD5Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Login", value = "/authentication/login")
public class Login extends ControllerServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            session = request.getSession(true);

            Optional<String> username = Optional.ofNullable(request.getParameter("username")).filter(s -> !s.isEmpty());
            Optional<String> password = Optional.ofNullable(request.getParameter("password")).filter(s -> !s.isEmpty());
            if (!(username.isPresent() && password.isPresent())) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            AccountDataAccess dataAccess = new AccountDataAccess();
            Optional<Account> account = dataAccess.getOne(username.get());
            if (account.isPresent()) {
                String hash = MD5Util.md5(password.get());
                if (Objects.equals(hash, account.get().getPassword())) {
                    session.setAttribute("user", account.get());
                    Logger.getLogger(Login.class.getName()).log(Level.INFO, String.format("Logged in account: %s [%d]; JCOMI_SESSION=%s", account.get().getUsername(), account.get().getId(), session.getId()));
                    response.sendRedirect(request.getContextPath()); // redirect to home page
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | IOException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Error while logging in", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
