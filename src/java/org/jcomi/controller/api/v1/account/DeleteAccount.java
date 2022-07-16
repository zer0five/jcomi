package org.jcomi.controller.api.v1.account;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.account.AccountDataAccess;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DeleteAccount", value = "/api/v1/account/delete")
@WebFilter(filterName = "DeleteAccountFilter", servletNames = {"DeleteAccount"})
public class DeleteAccount extends ControllerServlet implements Filter {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (!id.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (AccountDataAccess.getInstance().delete(id.get()) == 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            Logger.getLogger(DeleteAccount.class.getName()).log(Level.SEVERE, "Error deleting account", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        try {
            Optional<Object> rawAccount = Optional.ofNullable(request.getSession(false))
                .map(s -> s.getAttribute("user"));
            if (!rawAccount.isPresent()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Account account = (Account) rawAccount.get();
            if (account.isAdmin()) {
                chain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
