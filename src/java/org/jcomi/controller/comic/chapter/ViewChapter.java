package org.jcomi.controller.comic.chapter;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.controller.comic.AddComic;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.comic.ComicDataAccess;
import org.jcomi.entity.comic.chapter.Chapter;
import org.jcomi.entity.comic.chapter.ChapterDataAccess;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ViewComic", value = "/comic/view")
public class ViewChapter extends ControllerServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional<Integer> comicId = Optional.ofNullable(request.getParameter("id")).map(Integer::parseInt);
            Optional<Integer> chapter = Optional.ofNullable(request.getParameter("chapter")).map(Integer::parseInt);
            if (!comicId.isPresent() || !chapter.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ComicDataAccess comicDataAccess = new ComicDataAccess();
            Optional<Comic> comicOptional = comicDataAccess.getOne(comicId.get());
            if (!comicOptional.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comic not found");
                return;
            }
            Comic comic = comicOptional.get();
            ChapterDataAccess chapterDataAccess = new ChapterDataAccess();
            Optional<Chapter> chapterO = chapterDataAccess.getChapter(comic.getId(), chapter.get());
            if (!chapterO.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Chapter not found");
                return;
            }
            comic.setViews(comic.getViews() + 1);
            comicDataAccess.update(comic);
            request.setAttribute("comic", comic);
            request.setAttribute("chapter", chapterO.get());
            request.getRequestDispatcher("/comic/view.jsp").forward(request, response);
            
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error while getting comic info", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
