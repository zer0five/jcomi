package org.jcomi.controller.comic;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcomi.entity.comic.ComicDataAccess;

@WebServlet(name = "DeleteComic", value = "/comic/delete")
@WebFilter(filterName = "DeleteComicFilter", servletNames = {"DeleteComic"})
public class DeleteComic extends ControllerServlet implements Filter {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (!id.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ComicDataAccess dataAccess = new ComicDataAccess();
            if (dataAccess.delete(id.get()) == 1) {
                response.sendRedirect(request.getContextPath()); // redirect to home page
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            Logger.getLogger(DeleteComic.class.getName()).log(Level.SEVERE, "Error deleting comic", e);
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
