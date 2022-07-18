package org.jcomi.controller.api.v1.account;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.account.AccountDataAccess;
import org.jcomi.util.MD5Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "EditAccount", value = "/api/v1/account/edit")
public class EditAccount extends ControllerServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            Object rawAccount = session.getAttribute("user");
            if (rawAccount == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Account currentUser = (Account) rawAccount;

            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (!id.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (!currentUser.isAdmin() || currentUser.getId() != id.get()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Optional<String> displayName = Optional.ofNullable(request.getParameter("new-display-name"));
            Optional<String> email = Optional.ofNullable(request.getParameter("new-email"));
            Optional<String> password = Optional.ofNullable(request.getParameter("new-password")).map(MD5Util::md5);
            AccountDataAccess dataAccess = new AccountDataAccess();
            Optional<Account> accountOptional = dataAccess.getOne(id.get());
            if (!accountOptional.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Account account = accountOptional.get();
            displayName.ifPresent(account::setDisplayName);
            email.ifPresent(account::setEmail);
            password.ifPresent(account::setPassword);

            if (currentUser.isAdmin()) {
                Optional<Boolean> banned = Optional.ofNullable(request.getParameter("banned")).map(Boolean::parseBoolean);
                Optional<Boolean> isAdmin = Optional.ofNullable(request.getParameter("is-admin")).map(Boolean::parseBoolean);
                Optional<Boolean> isUploader = Optional.ofNullable(request.getParameter("is-uploader")).map(Boolean::parseBoolean);
                banned.ifPresent(account::setBanned);
                isAdmin.ifPresent(account::setAdmin);
                isUploader.ifPresent(account::setUploader);
            }

            if (dataAccess.update(account) == 1) {
                session.setAttribute("user", account);
                Optional<String> callback = Optional.ofNullable(request.getParameter("id")).filter(s -> !s.isEmpty());
                if (callback.isPresent()) {
                    response.sendRedirect(callback.get());
                } else {
                    response.sendRedirect(request.getContextPath()); // redirect to home page
                }
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            Logger.getLogger(EditAccount.class.getName()).log(Level.SEVERE, null, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
