package org.jcomi.controller.comic;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.comic.ComicDataAccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ComicInformation", value = "/comic/information")
public class ComicInformation extends ControllerServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // forward to comic/info.jsp
            ComicDataAccess comicDataAccess = new ComicDataAccess();
            Optional<Integer> id = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            if (!id.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Optional<Comic> comic = comicDataAccess.getOne(id.get());
            if (!comic.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comic not found");
                return;
            }
            request.setAttribute("comic", comic.get());
            request.getRequestDispatcher("/comic/info.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(ComicInformation.class.getName()).log(Level.SEVERE, "Error while getting comic info", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
