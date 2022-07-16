package org.jcomi.controller.authentication;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Logout", value = "/authentication/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession(false).invalidate();
            response.sendRedirect(request.getContextPath());
        } catch (Exception e) {
            Logger.getLogger(Logout.class.getName()).log(Level.SEVERE, "Error while logging out", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
