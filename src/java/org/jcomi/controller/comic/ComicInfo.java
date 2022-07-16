package org.jcomi.controller.comic;

import org.jcomi.controller.ControllerServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ComicInfo", value = "/comic/info")
public class ComicInfo extends ControllerServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // forward to comic/info.jsp
            request.getRequestDispatcher("/comic/info.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(ComicInfo.class.getName()).log(Level.SEVERE, "Error while getting comic info", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
